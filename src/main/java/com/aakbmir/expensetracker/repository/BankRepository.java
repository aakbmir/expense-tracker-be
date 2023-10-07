package com.aakbmir.expensetracker.repository;

import com.aakbmir.expensetracker.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BankRepository extends JpaRepository<Bank, Long> {

    @Query("SELECT b FROM Bank b order by date asc")
    List<Bank> findAll();

    @Query("SELECT b FROM Bank b where b.name=:name order by date desc")
    List<Bank> findByName(String name);

    @Query("SELECT b FROM Bank b WHERE YEAR(b.date) = :year AND MONTH(b.date) = :month order by b.date desc")
    List<Bank> findByMonthAndYear(int year, int month);

    @Query("SELECT b FROM Bank b WHERE YEAR(b.date) = :year order by date desc")
    List<Bank> findByYear(int year);
}
