package com.aakbmir.expensetracker.usecases.income.service;

import com.aakbmir.expensetracker.usecases.income.repository.IncomeRepository;
import com.aakbmir.expensetracker.usecases.income.repository.entity.Income;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IncomeService {

    @Autowired
    IncomeRepository incomeRepository;

    public Income saveIncome(Income income) {
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