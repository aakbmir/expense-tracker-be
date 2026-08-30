package com.aakbmir.expensetracker.usecases.budget.repository;

import com.aakbmir.expensetracker.usecases.budget.repository.entity.Budget;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface BudgetRepository extends JpaRepository<Budget, Long> {

    Optional<Budget> findByCategory_CategoryId(Long categoryId);

    @Query("SELECT b FROM Budget b WHERE YEAR(b.date) = :year AND MONTH(b.date) = :month")
    List<Budget> findByMonthAndYear(int year, int month);

    @Modifying
    @Transactional
    @Query("UPDATE Budget b SET budgetAmount = :budgetAmount WHERE budgetId = :budgetId")
    int updateBudget(@NotNull @Positive BigDecimal budgetAmount, Long budgetId);

    @Query("SELECT b FROM Budget b WHERE YEAR(b.date) = :year")
    List<Budget> findByYear(int year);

    List<Budget> findAllByOrderByDateAsc();

    /*    List<Budget> findByCategory(String budgetName);






    @Query("SELECT sum(price) FROM Budget e WHERE YEAR(e.date) = :year")
    double fetchSumByYear(int year);

    @Query("SELECT TO_CHAR(date, 'YYYY-MM') AS month, SUM(price) AS total_price FROM Budget ec GROUP BY TO_CHAR(date, 'YYYY-MM') ORDER BY month")
    List<Object[]> fetchSumByYearAndMonth();*/
}