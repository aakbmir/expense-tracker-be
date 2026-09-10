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

public interface BudgetRepository extends JpaRepository<Budget, Long> {

    @Query("  SELECT b FROM Budget b JOIN FETCH b.category WHERE YEAR(b.date) = :year AND MONTH(b.date) = :month order by b.date desc")
    List<Budget> findBudgetAndCatByMonthAndYear(int year, int month);

    @Modifying
    @Transactional
    @Query("UPDATE Budget b SET budgetAmount = :budgetAmount WHERE budgetId = :budgetId")
    void updateBudget(@NotNull @Positive BigDecimal budgetAmount, Long budgetId);

    @Query("SELECT b FROM Budget b WHERE YEAR(b.date) = :year")
    List<Budget> findByYear(int year);

    @Query("SELECT b FROM Budget b JOIN FETCH b.category order by b.date desc")
    List<Budget> findAllDataByCategory();

    @Query("SELECT e FROM Budget e JOIN FETCH e.category c WHERE c.category=:category AND" +
            " YEAR(e.date) = :year AND MONTH(e.date) = :month order by e.date desc")
    List<Budget> findBudgetAndCatByCategoryAndMonthAndYear(String category, int year, int month);

    @Query("SELECT e FROM Budget e JOIN FETCH e.category c WHERE c.superCategory=:superCategory AND" +
            " YEAR(e.date) = :year AND MONTH(e.date) = :month order by e.date desc")
    List<Budget> findBudgetAndCatBySuperCategoryAndMonthAndYear(String superCategory, int year, int month);

    @Query("SELECT e FROM Budget e JOIN FETCH e.category c WHERE c.mainCategory=:mainCategory AND" +
            " YEAR(e.date) = :year AND MONTH(e.date) = :month order by e.date desc")
    List<Budget> findBudgetAndCatByMainCategoryAndMonthAndYear(String mainCategory, int year, int month);
}