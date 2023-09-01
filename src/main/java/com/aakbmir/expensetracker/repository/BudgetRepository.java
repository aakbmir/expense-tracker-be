package com.aakbmir.expensetracker.repository;

import com.aakbmir.expensetracker.entity.Budget;
import com.aakbmir.expensetracker.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BudgetRepository extends JpaRepository<Budget, Long> {
    Budget findByCategory(String budgetName);

    List<Budget> findAllByOrderByCategoryAsc();

}
