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
import java.util.Optional;

@Service
public class BudgetService {

    @Autowired
    BudgetRepository budgetRepository;

    @Autowired
    CategoryService categoryService;

    public List<Budget> addAllBudgets() {
        List<Category> categoryList = categoryService.getAllCategoriesByMonthAndYear(0,0);
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

    public Budget saveBudget(Budget budget) {
        return budgetRepository.save(budget);
    }

    public List<Budget> findByBudget(String category) {
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
        return budgetRepository.findByMonthAndYear(year, month);
    }
}
