package com.aakbmir.expensetracker.repository;

import com.aakbmir.expensetracker.entity.Income;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IncomeRepository extends JpaRepository<Income, Long> {
    Income findByType(String type);

    List<Income> findAllByOrderByTypeAsc();

/*    List<Income> findByMonthYear(String concatField);

    @Query("SELECT DISTINCT p.incomeName FROM Income p")
    List<String> fetchIncomeName();*/
}
