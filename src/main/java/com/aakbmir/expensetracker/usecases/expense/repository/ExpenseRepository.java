package com.aakbmir.expensetracker.usecases.expense.repository;

import com.aakbmir.expensetracker.usecases.expense.repository.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    @Query("SELECT e FROM Expense e WHERE YEAR(e.date) = :year AND MONTH(e.date) = :month order by e.date desc")
    List<Expense> findByMonthAndYear(int year, int month);

    @Query("SELECT e FROM Expense e WHERE YEAR(e.date) = :year order by date desc")
    List<Expense> findByYear(int year);

    @Query("SELECT e FROM Expense e JOIN FETCH e.category order by e.date desc")
    List<Expense> findAllDataByCategory();

    @Query("SELECT e FROM Expense e where e.category=:category order by date desc")
    List<Expense> findCategory(String category);

    @Query("SELECT e FROM Expense e JOIN FETCH e.category c WHERE c.category=:category AND" +
            " YEAR(e.date) = :year AND MONTH(e.date) = :month order by e.date desc")
    List<Expense> findExpenseAndCatByCategoryAndMonthAndYear(String category, int year, int month);

    @Query("SELECT e FROM Expense e JOIN FETCH e.category c WHERE c.superCategory=:superCategory AND" +
            " YEAR(e.date) = :year AND MONTH(e.date) = :month order by e.date desc")
    List<Expense> findExpenseAndCatBySuperCategoryAndMonthAndYear(String superCategory, int year, int month);

    @Query("SELECT e FROM Expense e JOIN FETCH e.category c WHERE c.mainCategory=:mainCategory AND" +
            " YEAR(e.date) = :year AND MONTH(e.date) = :month order by e.date desc")
    List<Expense> findExpenseAndCatByMainCategoryAndMonthAndYear(String mainCategory, int year, int month);
}
