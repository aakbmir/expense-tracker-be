package com.aakbmir.expensetracker.repository;

import com.aakbmir.expensetracker.entity.Income;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IncomeRepository extends JpaRepository<Income, Long> {
    Income findByName(String name);

    List<Income> findAllByOrderByNameAsc();

}

