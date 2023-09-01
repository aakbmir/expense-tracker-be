package com.aakbmir.expensetracker.repository;

import com.aakbmir.expensetracker.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    Expense findByItem(String expenseName);
}
