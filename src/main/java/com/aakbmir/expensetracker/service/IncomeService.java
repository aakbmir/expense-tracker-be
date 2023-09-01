package com.aakbmir.expensetracker.service;

import com.aakbmir.expensetracker.entity.Income;
import com.aakbmir.expensetracker.entity.Income;
import com.aakbmir.expensetracker.repository.IncomeRepository;
import com.aakbmir.expensetracker.repository.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncomeService {

    @Autowired
    IncomeRepository incomeRepository;

    public Income saveIncome(Income income) {
        return incomeRepository.save(income);
    }

    public Income findByType(String incomeName) {
        return incomeRepository.findByType(incomeName);
    }

    public void deleteIncome(Long id) {
        incomeRepository.deleteById(id);
    }

    public Income updateIncome(Income income) {
        return incomeRepository.save(income);
    }

    public List<Income> getAllIncome() {
        return incomeRepository.findAllByOrderByTypeAsc();
    }

/*    public List<Income> filterIncome(String month, String year) {
        String concatField = month+"-"+year;
        return incomeRepository.findByMonthYear(concatField);
    }

    public List<String> fetchUmbrellaIncome() {
        return incomeRepository.fetchIncomeName();
    }*/
}
