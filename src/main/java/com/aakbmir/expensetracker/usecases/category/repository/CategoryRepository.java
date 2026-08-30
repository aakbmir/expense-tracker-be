package com.aakbmir.expensetracker.usecases.category.repository;

import com.aakbmir.expensetracker.usecases.category.repository.entity.Category;
import com.aakbmir.expensetracker.utils.enums.CategoryStatus;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findAllByOrderByMainCategoryAscSuperCategoryAscCategoryAsc();

    @Query("SELECT DISTINCT p.superCategory FROM Category p order by superCategory asc")
    List<String> fetchDistinctSuperCategories();

    @Query("SELECT c FROM Category c WHERE YEAR(c.date) = :year AND MONTH(c.date) = :month order by c.mainCategory," +
            " superCategory, category asc")
    List<Category> findAllByOrderByCategoryAsc(int year, int month);

    @Query("SELECT c FROM Category c WHERE c.status =:status AND YEAR(c.date) = :year AND MONTH(c.date) = :month " +
            "order by c.mainCategory, superCategory, category asc")
    List<Category> findActiveByOrderByCategoryAsc(CategoryStatus status, int year, int month);

    @Query("SELECT c FROM Category c order by c.mainCategory, superCategory, category asc")
    List<Category> findAllByOrderByCategoryAsc();

    @Modifying
    @Transactional
    @Query("UPDATE Category c SET c.status =:status where c.categoryId=:id")
    void updateCategoryStatus(@NotNull Long id, @NotNull CategoryStatus status);
}
