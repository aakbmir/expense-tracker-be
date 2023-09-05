package com.aakbmir.expensetracker.repository;

import com.aakbmir.expensetracker.entity.Budget;
import com.aakbmir.expensetracker.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BudgetRepository extends JpaRepository<Budget, Long> {
    Budget findByCategory(String budgetName);

    List<Budget> findAllByOrderByParentAscCategoryAsc();

    @Query(value = "select * from budget b order by b.parent, b.category asc", nativeQuery = true)
    List<Budget> fetchDateByDate(String date);

    @Query("SELECT b FROM Budget b WHERE YEAR(b.date) = :year AND MONTH(b.date) = :month order by b.parent, b.category asc")
    List<Budget> findByMonthAndYear(int year, int month);

    @Query(value= "SELECT b.price FROM budget b WHERE EXTRACT(YEAR FROM b.date) = :year AND EXTRACT(MONTH FROM b.date) = :month", nativeQuery = true)
    List<Double> getSumByMonthAndYear(int year, int month);
}