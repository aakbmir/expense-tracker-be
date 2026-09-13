package com.aakbmir.expensetracker.usecases.category.repository;

import com.aakbmir.expensetracker.usecases.category.repository.entity.Category;
import com.aakbmir.expensetracker.usecases.reports.repository.CategoryExpenseProjection;
import com.aakbmir.expensetracker.utils.enums.CategoryStatus;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category, Long> {

  List<Category> findAllByOrderByMainCategoryAscSuperCategoryAscCategoryAsc();

  @Query("SELECT DISTINCT p.superCategory FROM Category p order by superCategory asc")
  List<String> fetchDistinctSuperCategories();

  @Query(
      "SELECT c FROM Category c WHERE YEAR(c.date) = :year AND MONTH(c.date) = :month order by c.mainCategory,"
          + " superCategory, category asc")
  List<Category> findAllByOrderByCategoryAsc(int year, int month);

  @Query(
      "SELECT c FROM Category c WHERE c.status =:status AND YEAR(c.date) = :year AND MONTH(c.date) = :month "
          + "order by c.mainCategory, superCategory, category asc")
  List<Category> findActiveByOrderByCategoryAsc(CategoryStatus status, int year, int month);

  @Query("SELECT c FROM Category c order by c.mainCategory, superCategory, category asc")
  List<Category> findAllByOrderByCategoryAsc();

  @Modifying
  @Transactional
  @Query("UPDATE Category c SET c.status =:status where c.categoryId=:id")
  void updateCategoryStatus(@NotNull Long id, @NotNull CategoryStatus status);

  @Query(
      """
                SELECT new com.aakbmir.expensetracker.usecases.reports.repository.CategoryExpenseProjection(
                    c.categoryId, c.mainCategory, c.superCategory,
                    c.category,c.budgetAmount,COALESCE(SUM(e.amount), 0)
                )
                FROM Category c LEFT JOIN Expense e ON e.category.categoryId = c.categoryId
                AND YEAR(e.date) = :year AND MONTH(e.date) = :month
                WHERE YEAR(c.date) = :year AND MONTH(c.date) = :month
                GROUP BY c.categoryId, c.mainCategory, c.superCategory, c.category, c.budgetAmount
                ORDER BY c.mainCategory, c.superCategory, c.category
            """)
  List<CategoryExpenseProjection> findMonthlyCategoryExpenses(
      @Param("year") int year, @Param("month") int month);
}
