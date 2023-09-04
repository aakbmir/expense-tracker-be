package com.aakbmir.expensetracker.service;

import com.aakbmir.expensetracker.entity.Expense;
import com.aakbmir.expensetracker.entity.Expense;
import com.aakbmir.expensetracker.repository.ExpenseRepository;
import com.aakbmir.expensetracker.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {
    
    @Autowired
    ExpenseRepository expenseRepository;

    public Expense saveExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    public List<Expense> findByCategory(String expense) {
        return expenseRepository.findByCategory(expense);
    }

    public Optional<Expense> findById(Long id) {
        return expenseRepository.findById(id);
    }

    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }

    public Expense updateExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAllByOrderByCategoryAsc();
    }

    public List<Expense> findByMonthAndYear(int year, int month) {
        return expenseRepository.findByMonthAndYear(year, month);
    }
}