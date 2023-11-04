package com.aakbmir.expensetracker.repository;

import com.aakbmir.expensetracker.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    @Query("SELECT e FROM Expense e order by date desc")
    List<Expense> findAllByCategory();

    @Query("SELECT e FROM Expense e where e.category=:category order by date desc")
    List<Expense> findCategory(String category);

    @Query("SELECT e FROM Expense e where e.superCategory=:superCategory order by date desc")
    List<Expense> findSuperCategory(String superCategory);

    @Query("SELECT e FROM Expense e where e.parentCategory=:parentCategory order by date desc")
    List<Expense> findParentCategory(String parentCategory);

    List<Expense> findAllByOrderByDateAsc();

    @Query("SELECT e FROM Expense e WHERE YEAR(e.date) = :year AND MONTH(e.date) = :month order by e.date desc")
    List<Expense> findByMonthAndYear(int year, int month);

    @Query("SELECT e FROM Expense e WHERE YEAR(e.date) = :year order by date desc")
    List<Expense> findByYear(int year);

    @Query("SELECT e FROM Expense e WHERE e.parentCategory=:parentCategory and category != 'Stocks' order by e.date desc")
    List<Expense> fetchParentCategoryExpense(String parentCategory);

    @Query("SELECT sum(price) FROM Expense e WHERE YEAR(e.date) = :year")
    double fetchSumByYear(int year);

    @Query("SELECT TO_CHAR(date, 'YYYY-MM') AS month, SUM(price) AS total_price FROM Expense ec  GROUP BY TO_CHAR(date, 'YYYY-MM') ORDER BY month")
    List<Object[]> fetchSumByYearAndMonth();

}
