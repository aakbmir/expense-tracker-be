package com.aakbmir.expensetracker.repository;

import com.aakbmir.expensetracker.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByCategory(String category);

    List<Expense> findAllByOrderByCategoryAsc();

    @Query("SELECT i FROM Expense i WHERE YEAR(i.date) = :year AND MONTH(i.date) = :month")
    List<Expense> findByMonthAndYear(int year, int month);

    @Query(value= "SELECT SUM(b.price) FROM expense b WHERE EXTRACT(YEAR FROM b.date) = :year AND EXTRACT(MONTH FROM b.date) = :month", nativeQuery = true)
    int getSumByMonthAndYear(int year, int month);
}
