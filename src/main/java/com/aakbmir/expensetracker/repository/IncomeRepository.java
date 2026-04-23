package com.aakbmir.expensetracker.repository;

import com.aakbmir.expensetracker.entity.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {

    @Query("SELECT e FROM Income e order by date desc")
    List<Income> findAllByCategory();

    @Query("SELECT e FROM Income e where e.category=:category order by date desc")
    List<Income> findCategory(String category);

    List<Income> findAllByOrderByDateAsc();

    @Query("SELECT e FROM Income e WHERE YEAR(e.date) = :year AND MONTH(e.date) = :month order by e.date desc")
    List<Income> findByMonthAndYear(int year, int month);

    @Query("SELECT e FROM Income e WHERE YEAR(e.date) = :year order by date desc")
    List<Income> findByYear(int year);

    @Query("SELECT sum(price) FROM Income e WHERE YEAR(e.date) = :year")
    double fetchSumByYear(int year);

    @Query("SELECT TO_CHAR(date, 'YYYY-MM') AS month, SUM(price) AS total_price FROM Income ec  GROUP BY TO_CHAR(date, 'YYYY-MM') ORDER BY month")
    List<Object[]> fetchSumByYearAndMonth();

}
