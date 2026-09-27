package com.aakbmir.expensetracker.usecases.reports.repository;

import com.aakbmir.expensetracker.usecases.expense.repository.entity.Expense;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReportsRepository extends JpaRepository<Expense, Long> {

  @Query(
      "SELECT i FROM Expense i WHERE YEAR(i.date) = :year AND MONTH(i.date) = :month and i.category.category=:category order by date desc")
  List<Expense> findCategoryByMonthAndYear(int year, int month, String category);

  /*    @Query("SELECT i FROM Expense i WHERE YEAR(i.date) = :year AND MONTH(i.date) = :month and i.superCategory=:superCategory order by date desc")
  List<Expense> findSuperCategoryByMonthAndYear(int year, int month, String superCategory);

  @Query("SELECT DISTINCT e.category FROM Category e order by e.category asc")
  List<String> findDistinctCategoriesValue();*/
}
