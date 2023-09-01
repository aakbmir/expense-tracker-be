package com.aakbmir.expensetracker.service;

import com.aakbmir.expensetracker.entity.Expense;
import com.aakbmir.expensetracker.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpenseService {

    @Autowired
    ExpenseRepository expenseRepository;

    public Expense saveExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    public Expense getExpense(String expenseName) {
        return expenseRepository.findByItem(expenseName);
    }

    public void deleteExpense(Expense expense) {
        expenseRepository.delete(expense);
    }

    public Expense updateExpense(Expense expense) {
        return expenseRepository.save(expense);
    }
}
