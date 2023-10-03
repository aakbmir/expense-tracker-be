package com.aakbmir.expensetracker.service;

import com.aakbmir.expensetracker.entity.Category;
import com.aakbmir.expensetracker.entity.Expense;
import com.aakbmir.expensetracker.repository.ExpenseRepository;
import com.aakbmir.expensetracker.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExpenseService {
    
    @Autowired
    ExpenseRepository expenseRepository;

    @Autowired
    CategoryService categoryService;

    @Autowired
    CommonUtils commonUtils;

    public Expense saveExpense(Expense expense) {
        Category category = null;
        for (Category cat : commonUtils.fetchAllCategories()) {
            if (expense.getCategory().equalsIgnoreCase(cat.getCategory())) {
                category = cat;
                break;
            }
        }
        expense.setParentCategory(category.getParentCategory());
        expense.setSuperCategory(category.getSuperCategory());
        return expenseRepository.save(expense);
    }

    public List<Expense> findByCategory(String expense) {
        if(expense == null || expense.equalsIgnoreCase("")) {
            return expenseRepository.findAllByCategory();
        } else {
            return expenseRepository.findCategory(expense);
        }
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
        List<Expense> expenseList = expenseRepository.findByMonthAndYear(year, month);
        return expenseList;
    }
}