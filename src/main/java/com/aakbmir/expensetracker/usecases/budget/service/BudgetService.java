package com.aakbmir.expensetracker.usecases.budget.service;

import com.aakbmir.expensetracker.usecases.budget.api.dto.BudgetDTO;
import com.aakbmir.expensetracker.usecases.budget.repository.BudgetRepository;
import com.aakbmir.expensetracker.usecases.budget.repository.entity.Budget;
import com.aakbmir.expensetracker.usecases.budget.service.mapper.BudgetMapper;
import com.aakbmir.expensetracker.usecases.category.bff.dto.CategoryApiDTO;
import com.aakbmir.expensetracker.usecases.category.repository.entity.Category;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.aakbmir.expensetracker.usecases.budget.service.mapper.BudgetMapper.mapToBudget;
import static com.aakbmir.expensetracker.usecases.category.service.mapper.CategoryMapper.mapToCategory;

@Service
@RequiredArgsConstructor
@Slf4j
public class BudgetService {

    private final BudgetRepository budgetRepository;

    public void saveBudgetWithCategory(CategoryApiDTO categoryApiDTO) {
        if (categoryApiDTO.category().equalsIgnoreCase("Gold and Silver") ||
                categoryApiDTO.category().equalsIgnoreCase("Emergency Fund") ||
                categoryApiDTO.category().equalsIgnoreCase("ETF") ||
                categoryApiDTO.category().equalsIgnoreCase("Home EMI") ||
                categoryApiDTO.category().equalsIgnoreCase("LIC") ||
                categoryApiDTO.category().equalsIgnoreCase("PPF") ||
                categoryApiDTO.category().equalsIgnoreCase("Stocks") ||
                categoryApiDTO.category().equalsIgnoreCase("Others")) {
            log.info("Ignored Budget Save When Saving Category {}", categoryApiDTO.category());
        } else {
            BudgetDTO budgetDTO = BudgetDTO.builder()
                    .categoryApiDTO(categoryApiDTO)
                    .budgetAmount(new BigDecimal("0.0"))
                    .date(categoryApiDTO.date())
                    .build();
            saveBudget(budgetDTO);
        }
    }

    public void saveBudget(BudgetDTO budgetDTO) {
        Category category = mapToCategory(budgetDTO.categoryApiDTO());
        Budget budget = mapToBudget(budgetDTO, category);
        budgetRepository.save(budget);
    }

    public List<BudgetDTO> addAllBudgets(List<Category> categoryList) {
        List<Budget> budgetList = new ArrayList<>();
        for (Category cat : categoryList) {
            if (cat.getCategory().equalsIgnoreCase("Gold and Silver") ||
                    cat.getCategory().equalsIgnoreCase("Emergency Fund") ||
                    cat.getCategory().equalsIgnoreCase("ETF") ||
                    cat.getCategory().equalsIgnoreCase("Home EMI") ||
                    cat.getCategory().equalsIgnoreCase("LIC") ||
                    cat.getCategory().equalsIgnoreCase("PPF") ||
                    cat.getCategory().equalsIgnoreCase("Stocks") ||
                    cat.getCategory().equalsIgnoreCase("Others")) {
                log.info("Ignored Budget Save When Saving Budgets {}", cat.getCategory());
            } else {
                Budget budgetObj = createBudgetObject(cat);
                budgetList.add(budgetObj);
            }
        }
        budgetList = budgetRepository.saveAll(budgetList);
        return budgetList.stream()
                .map(BudgetMapper::mapToBudgetDTO)
                .toList();
    }

    private Budget createBudgetObject(Category cat) {
        return Budget.builder()
                .date(cat.getDate())
                .budgetAmount(new BigDecimal("0.0"))
                .category(cat)
                .build();
    }

    public void updateBudget(BudgetDTO budgetDTO) {
        budgetRepository.updateBudget(budgetDTO.budgetAmount(), budgetDTO.budgetId());
    }

    public List<BudgetDTO> findByMonthAndYear(int year, int month) {
        List<Budget> budgetList = budgetRepository.findByMonthAndYear(year, month);
        return budgetList.stream()
                .map(BudgetMapper::mapToBudgetDTO)
                .toList();
    }
}
