package com.aakbmir.expensetracker.service;

import com.aakbmir.expensetracker.entity.Budget;
import com.aakbmir.expensetracker.entity.Category;
import com.aakbmir.expensetracker.repository.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class BudgetService {

    @Autowired
    BudgetRepository budgetRepository;

    @Autowired
    CategoryService categoryService;

    public List<Budget> addAllBudgets(int year, int month) {
        List<Category> categoryList = categoryService.getAllCategoriesByMonthAndYear(year, month);
        List<Budget> budgetList = new ArrayList<>();
        for (Category cat : categoryList) {
            Budget budgetObj = Budget.builder()
                    .price(new BigDecimal("0.00"))
                    .date(Instant.now())
                    .category(cat.getCategory())
                    .subCategory(cat.getSubCategory())
                    .mainCategory(cat.getMainCategory())
                    .categoryGroup(cat.getCategoryGroup())
                    .build();
            budgetList.add(budgetObj);
        }
        budgetList = budgetRepository.saveAll(budgetList);
        return budgetList;
    }

    public void saveBudget(Budget budget) {
        budgetRepository.save(budget);
    }

    public List<Budget> findByBudget(String category) {
        return budgetRepository.findByCategory(category);
    }

    public Budget updateBudget(Budget budget) {
        return budgetRepository.save(budget);
    }

    public List<Budget> findByMonthAndYear(int year, int month) {
        return budgetRepository.findByMonthAndYear(year, month);
    }
}
