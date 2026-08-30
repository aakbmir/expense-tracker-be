package com.aakbmir.expensetracker.usecases.budget.service.mapper;

import com.aakbmir.expensetracker.usecases.budget.api.dto.BudgetDTO;
import com.aakbmir.expensetracker.usecases.budget.repository.entity.Budget;
import com.aakbmir.expensetracker.usecases.category.repository.entity.Category;

import java.time.ZonedDateTime;

import static com.aakbmir.expensetracker.config.BusinessClock.BUSINESS_ZONE;
import static com.aakbmir.expensetracker.usecases.category.service.mapper.CategoryMapper.mapToCategoryApiDTO;

public class BudgetMapper {

    public static Budget mapToBudget(BudgetDTO budgetDTO, Category category) {

        return Budget.builder()
                .category(category)
                .budgetAmount(budgetDTO.budgetAmount())
                .date(budgetDTO.date().toInstant())
                .build();
    }

    public static BudgetDTO mapToBudgetDTO(Budget budget) {

        ZonedDateTime date = budget.getDate().atZone(BUSINESS_ZONE);
        return BudgetDTO.builder()
                .budgetId(budget.getBudgetId())
                .categoryApiDTO(mapToCategoryApiDTO(budget.getCategory()))
                .budgetAmount(budget.getBudgetAmount())
                .date(date)
                .build();
    }
}
