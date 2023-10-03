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

    List<Expense> findAllByOrderByCategoryAsc();

    List<Expense> findAllByOrderByDateAsc();

    @Query("SELECT i FROM Expense i WHERE YEAR(i.date) = :year AND MONTH(i.date) = :month order by date desc")
    List<Expense> findByMonthAndYear(int year, int month);
/*
    @Query(value= "SELECT b.price FROM expense b WHERE EXTRACT(YEAR FROM b.date) = :year AND EXTRACT(MONTH FROM b.date) = :month", nativeQuery = true)
    List<Double> getSumByMonthAndYear(int year, int month);
    */
}
