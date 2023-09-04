package com.aakbmir.expensetracker.repository;

import com.aakbmir.expensetracker.entity.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReportsRepository extends JpaRepository<Income, Long> {
    Income findByName(String name);

    List<Income> findAllByOrderByNameAsc();

    @Query("SELECT i FROM Income i WHERE YEAR(i.date) = :year AND MONTH(i.date) = :month")
    List<Income> findByMonthAndYear(int year, int month);
}
