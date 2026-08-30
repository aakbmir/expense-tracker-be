package com.aakbmir.expensetracker.usecases.savings.repository;

import com.aakbmir.expensetracker.usecases.savings.repository.entity.Savings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavingsRepository extends JpaRepository<Savings, Long> {

/*    @Query("SELECT e FROM Expense e order by date desc")
    List<Expense> findAllByCategory();

    @Query("SELECT e FROM Expense e where e.category=:category order by date desc")
    List<Expense> findCategory(String category);

    @Query("SELECT e FROM Expense e where e.subCategory=:subCategory order by date desc")
    List<Expense> findSubCategory(String subCategory);

    @Query("SELECT e FROM Expense e where e.mainCategory=:mainCategory order by date desc")
    List<Expense> findMainCategory(String mainCategory);

    List<Expense> findAllByOrderByDateAsc();

    @Query("SELECT e FROM Expense e WHERE YEAR(e.date) = :year AND MONTH(e.date) = :month order by e.date desc")
    List<Expense> findByMonthAndYear(int year, int month);

    @Query("SELECT e FROM Expense e WHERE YEAR(e.date) = :year order by date desc")
    List<Expense> findByYear(int year);

    @Query("SELECT e FROM Expense e WHERE e.mainCategory=:mainCategory and category != 'Stocks' order by e.date desc")
    List<Expense> fetchMainCategoryExpense(String mainCategory);

    @Query("SELECT sum(price) FROM Expense e WHERE YEAR(e.date) = :year")
    double fetchSumByYear(int year);

    @Query("SELECT TO_CHAR(date, 'YYYY-MM') AS month, SUM(price) AS total_price FROM Expense ec  GROUP BY TO_CHAR(date, 'YYYY-MM') ORDER BY month")
    List<Object[]> fetchSumByYearAndMonth();*/

}
