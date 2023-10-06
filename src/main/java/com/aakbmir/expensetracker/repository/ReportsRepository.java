package com.aakbmir.expensetracker.repository;

import com.aakbmir.expensetracker.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReportsRepository extends JpaRepository<Expense, Long> {

    @Query("SELECT i FROM Expense i WHERE YEAR(i.date) = :year AND MONTH(i.date) = :month and i.category=:category order by date desc")
    List<Expense> findCategoryByMonthAndYear(int year, int month, String category);

    @Query("SELECT i FROM Expense i WHERE YEAR(i.date) = :year AND MONTH(i.date) = :month and i.superCategory=:superCategory order by date desc")
    List<Expense> findSuperCategoryByMonthAndYear(int year, int month, String superCategory);
}
