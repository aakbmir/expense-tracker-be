package com.aakbmir.expensetracker.usecases.savings.repository;

import com.aakbmir.expensetracker.usecases.savings.repository.entity.Savings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SavingsRepository extends JpaRepository<Savings, Long> {

    @Query("SELECT e FROM Savings e WHERE YEAR(e.date) = :year AND MONTH(e.date) = :month order by e.date desc")
    List<Savings> findByMonthAndYear(int year, int month);

    @Query("SELECT e FROM Savings e WHERE YEAR(e.date) = :year order by date desc")
    List<Savings> findByYear(int year);

    @Query("SELECT e FROM Savings e order by date desc")
    List<Savings> findAllByCategory();

    @Query("SELECT e FROM Savings e where e.category=:category order by date desc")
    List<Savings> findCategory(String category);

    List<Savings> findAllByOrderByDateAsc();

    /*@Query("SELECT e FROM Savings e where e.subCategory=:subCategory order by date desc")
    List<Savings> findSubCategory(String subCategory);

    @Query("SELECT e FROM Savings e where e.mainCategory=:mainCategory order by date desc")
    List<Savings> findMainCategory(String mainCategory);

    @Query("SELECT e FROM Savings e WHERE e.mainCategory=:mainCategory and category != 'Stocks' order by e.date desc")
    List<Savings> fetchMainCategorySavings(String mainCategory);

    @Query("SELECT sum(price) FROM Savings e WHERE YEAR(e.date) = :year")
    double fetchSumByYear(int year);

    @Query("SELECT TO_CHAR(date, 'YYYY-MM') AS month, SUM(price) AS total_price FROM Savings ec  GROUP BY TO_CHAR(date, 'YYYY-MM') ORDER BY month")
    List<Object[]> fetchSumByYearAndMonth();*/
}
