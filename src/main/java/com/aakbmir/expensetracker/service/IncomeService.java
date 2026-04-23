package com.aakbmir.expensetracker.service;

import com.aakbmir.expensetracker.entity.Category;
import com.aakbmir.expensetracker.entity.Income;
import com.aakbmir.expensetracker.repository.IncomeRepository;
import com.aakbmir.expensetracker.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IncomeService {

    @Autowired
    IncomeRepository incomeRepository;

    @Autowired
    CommonUtils commonUtils;

    public Income saveIncome(Income income) {
        Category category = null;
        for (Category cat : commonUtils.fetchAllCategories()) {
            if (income.getCategory().equalsIgnoreCase(cat.getCategory())) {
                category = cat;
                break;
            }
        }
        return incomeRepository.save(income);
    }

    public List<Income> findByCategory(String income) {
        if (income == null || income.equalsIgnoreCase("")) {
            return incomeRepository.findAllByCategory();
        } else {
            return incomeRepository.findCategory(income);
        }
    }

    public Optional<Income> findById(Long id) {
        return incomeRepository.findById(id);
    }

    public void deleteIncome(Long id) {
        incomeRepository.deleteById(id);
    }

    public List<Income> findByMonthAndYear(int year, int month) {
        return incomeRepository.findByMonthAndYear(year, month);
    }
}