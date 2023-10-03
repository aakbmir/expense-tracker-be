package com.aakbmir.expensetracker.repository;

import com.aakbmir.expensetracker.entity.Budget;
import com.aakbmir.expensetracker.entity.Budget;
import com.aakbmir.expensetracker.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BudgetRepository extends JpaRepository<Budget, Long> {
    Budget findByCategory(String budgetName);

    @Query(value = "select * from budget b order by b.category asc", nativeQuery = true)
    List<Budget> fetchDateByDate(String date);

    List<Budget> findAllByOrderByDateAsc();

    @Query("SELECT b FROM Budget b WHERE YEAR(b.date) = :year AND MONTH(b.date) = :month order by b.category asc")
    List<Budget> findByMonthAndYear(int year, int month);

    @Query(value= "SELECT b.price FROM budget b WHERE EXTRACT(YEAR FROM b.date) = :year AND EXTRACT(MONTH FROM b.date) = :month", nativeQuery = true)
    List<Double> getSumByMonthAndYear(int year, int month);

    @Query("SELECT b FROM Budget b WHERE YEAR(b.date) = :year AND MONTH(b.date) = :month and category = :category order by b.category asc")
    List<Budget> findCategoryByMonthAndYear(int year, int month, String category);

}