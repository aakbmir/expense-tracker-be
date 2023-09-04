package com.aakbmir.expensetracker.service;

import com.aakbmir.expensetracker.entity.Budget;
import com.aakbmir.expensetracker.entity.Expense;
import com.aakbmir.expensetracker.repository.BudgetRepository;
import com.aakbmir.expensetracker.repository.CategoryRepository;
import com.aakbmir.expensetracker.repository.ExpenseRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReportsService {

    @Autowired
    ExpenseRepository expenseRepository;

    @Autowired
    BudgetRepository budgetRepository;

    public Budget saveBudget(Budget budget) {
        return budgetRepository.save(budget);
    }

    public Budget findByBudget(String category) {
        return budgetRepository.findByCategory(category);
    }

    public Optional<Budget> findById(Long id) {
        return budgetRepository.findById(id);
    }

    public void deleteBudget(Long id) {
        budgetRepository.deleteById(id);
    }

    public Budget updateBudget(Budget budget) {
        return budgetRepository.save(budget);
    }

    public List<Budget> getAllBudgets() {
        return budgetRepository.findAllByOrderByParentAscCategoryAsc();
    }

    public List<Budget> getAllBudgetsByDate(String date) {
        return budgetRepository.fetchDateByDate(date);
    }

    public JSONObject calculateMonthlyView(int year, int month) {
        int totalBudget = budgetRepository.getSumByMonthAndYear(year, month);
        int totalExpense = expenseRepository.getSumByMonthAndYear(year, month);
        int totalDeviation = totalBudget - totalExpense;
        JSONObject json = new JSONObject();
        json.put("totalBudget", totalBudget);
        json.put("totalExpense", totalExpense);
        json.put("totalDeviation", totalDeviation);
        return json;
    }
}
