package com.aakbmir.expensetracker.controller;

import com.aakbmir.expensetracker.entity.Budget;
import com.aakbmir.expensetracker.entity.Expense;
import com.aakbmir.expensetracker.repository.BudgetRepository;
import com.aakbmir.expensetracker.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
public class TestController {

    @Autowired
    BudgetRepository budgetRepository;

    @Autowired
    ExpenseRepository expenseRepository;

    @GetMapping("/test")
    public void saveBudget() {
        List<Budget> totalBudget = budgetRepository.findAll();
        List<Expense> totalExpense = expenseRepository.findAll();

        Map<YearMonth, Double> monthlyExpenses = new HashMap<>();

        for (Expense expense : totalExpense) {
            LocalDate localDate = expense.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            YearMonth yearMonth = YearMonth.from(localDate);
            double price = expense.getPrice();
            monthlyExpenses.put(yearMonth, monthlyExpenses.getOrDefault(yearMonth, 0.0) + price);
        }
        System.out.println(monthlyExpenses);
    }

    

}

