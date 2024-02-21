package com.aakbmir.expensetracker.repository;

import com.aakbmir.expensetracker.entity.Budget;
import com.aakbmir.expensetracker.entity.Budget;
import com.aakbmir.expensetracker.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BudgetRepository extends JpaRepository<Budget, Long> {
    Budget findByCategory(String budgetName);

    List<Budget> findAllByOrderByDateAsc();

    @Query("SELECT b FROM Budget b WHERE YEAR(b.date) = :year AND MONTH(b.date) = :month order by b.parentCategory, superCategory, category asc")
    List<Budget> findByMonthAndYear(int year, int month);

    @Query("SELECT b FROM Budget b WHERE YEAR(b.date) = :year order by b.category asc")
    List<Budget> findByYear(int year);

    @Query("SELECT sum(price) FROM Budget e WHERE YEAR(e.date) = :year")
    double fetchSumByYear(int year);

    @Query("SELECT TO_CHAR(date, 'YYYY-MM') AS month, SUM(price) AS total_price FROM Budget ec GROUP BY TO_CHAR(date, 'YYYY-MM') ORDER BY month")
    List<Object[]> fetchSumByYearAndMonth();
}