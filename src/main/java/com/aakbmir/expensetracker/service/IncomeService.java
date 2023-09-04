package com.aakbmir.expensetracker.service;

import com.aakbmir.expensetracker.entity.Budget;
import com.aakbmir.expensetracker.entity.Income;
import com.aakbmir.expensetracker.repository.IncomeRepository;
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

    public Income findByName(String income) {
        return incomeRepository.findByName(income);
    }

    public Optional<Income> findById(Long id) {
        return incomeRepository.findById(id);
    }

    public void deleteIncome(Long id) {
        incomeRepository.deleteById(id);
    }

    public Income updateIncome(Income income) {
        return incomeRepository.save(income);
    }

    public List<Income> getAllIncomes() {
        return incomeRepository.findAllByOrderByNameAsc();
    }

    public List<Income> findByMonthAndYear(int year, int month) {
        return incomeRepository.findByMonthAndYear(year, month);
    }
}

