package com.aakbmir.expensetracker.service;

import com.aakbmir.expensetracker.entity.Budget;
import com.aakbmir.expensetracker.entity.Category;
import com.aakbmir.expensetracker.repository.BudgetRepository;
import com.aakbmir.expensetracker.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BudgetService {

    @Autowired
    BudgetRepository budgetRepository;

    @Autowired
    CategoryService categoryService;

    public List<Budget> addAllBudgets() {
        List<Category> categoryList = categoryService.getAllCategories();
        List<Budget> budgetList = new ArrayList<>();
        for (Category cat : categoryList) {
            Budget budgetObj = new Budget();
            budgetObj.setPrice(0);
            budgetObj.setDate(new Date());
            budgetObj.setCategory(cat.getCategory());
            budgetObj.setSuperCategory(cat.getSuperCategory());
            budgetObj.setParentCategory(cat.getParentCategory());
            budgetList.add(budgetObj);
        }
        budgetList = budgetRepository.saveAll(budgetList);
        return budgetList;
    }

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

    public List<Budget> findByMonthAndYear(int year, int month) {
        List<Budget> budgetList = budgetRepository.findByMonthAndYear(year, month);
        return budgetList;
    }
}
