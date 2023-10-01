package com.aakbmir.expensetracker.service;

import com.aakbmir.expensetracker.DTO.BudgetDTO;
import com.aakbmir.expensetracker.entity.Budget;
import com.aakbmir.expensetracker.entity.Category;
import com.aakbmir.expensetracker.repository.BudgetRepository;
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
        List<String> categories = categoryService.getDistinctCategories();

        List<Budget> budgetList = new ArrayList<>();
        for(String cat: categories) {
            Budget budgetObj = new Budget();
            budgetObj.setPrice(0);
            budgetObj.setDate(new Date());
            budgetObj.setCategory(cat);
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

    public List<BudgetDTO> findByMonthAndYear(int year, int month) {
        List<Budget> budgetList = budgetRepository.findByMonthAndYear(year, month);
        List<Category> categoryList = categoryService.getAllCategories();
        List<BudgetDTO> budgetDTOList = convertToBudgetDTO(budgetList, categoryList);
        return budgetDTOList;
    }

    private List<BudgetDTO> convertToBudgetDTO(List<Budget> budgetList, List<Category> categoryList) {
        List<BudgetDTO> mergedList = budgetList.stream()
                .map(list1Item -> {
                    Category list2Item = categoryList.stream()
                            .filter(item -> item.getCategory().equals(list1Item.getCategory()))
                            .findFirst()
                            .orElse(null);

                    BudgetDTO mergedResponse = new BudgetDTO();
                    mergedResponse.setId(list1Item.getId());
                    mergedResponse.setDate(list1Item.getDate());
                    mergedResponse.setCategory(list1Item.getCategory());
                    mergedResponse.setPrice(list1Item.getPrice());
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
