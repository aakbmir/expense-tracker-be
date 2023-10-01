package com.aakbmir.expensetracker.service;

import com.aakbmir.expensetracker.DTO.BudgetDTO;
import com.aakbmir.expensetracker.DTO.ExpenseDTO;
import com.aakbmir.expensetracker.entity.Category;
import com.aakbmir.expensetracker.entity.Expense;
import com.aakbmir.expensetracker.repository.ExpenseRepository;
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

    public Expense saveExpense(Expense expense) {
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

    public List<ExpenseDTO> findByMonthAndYear(int year, int month) {
        List<Expense> expenseList = expenseRepository.findByMonthAndYear(year, month);
        for(Expense exp: expenseList) {
            System.out.println(exp.getCategory() + " " + exp.getDate() + " " + exp.getNote() + " " + exp.getPrice());
        }
        List<Category> categoryList = categoryService.getAllCategories();
        List<ExpenseDTO> expenseDTOList = convertToExpenseDTO(expenseList, categoryList);
        return expenseDTOList;
    }

    private List<ExpenseDTO> convertToExpenseDTO(List<Expense> expenseList, List<Category> categoryList) {
        List<ExpenseDTO> mergedList = expenseList.stream()
                .map(list1Item -> {
                    Category list2Item = categoryList.stream()
                            .filter(item -> item.getCategory().equals(list1Item.getCategory()))
                            .findFirst()
                            .orElse(null);

                    ExpenseDTO mergedResponse = new ExpenseDTO();
                    mergedResponse.setId(list1Item.getId());
                    mergedResponse.setDate(list1Item.getDate());
                    mergedResponse.setCategory(list1Item.getCategory());
                    mergedResponse.setPrice(list1Item.getPrice());
                    mergedResponse.setNote(list1Item.getNote());
                    if (list2Item != null) {
                        mergedResponse.setParentCategory(list2Item.getParentCategory());
                        mergedResponse.setSuperCategory(list2Item.getSuperCategory());
                    }
                    return mergedResponse;
                })
                .collect(Collectors.toList());
        return mergedList;
    }
}