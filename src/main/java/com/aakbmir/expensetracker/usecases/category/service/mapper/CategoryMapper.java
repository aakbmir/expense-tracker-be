package com.aakbmir.expensetracker.usecases.category.service.mapper;

import com.aakbmir.expensetracker.usecases.category.bff.dto.CategoryApiDTO;
import com.aakbmir.expensetracker.usecases.category.repository.entity.Category;
import com.aakbmir.expensetracker.utils.enums.CategoryStatus;
import com.aakbmir.expensetracker.utils.enums.FinancialType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.time.ZonedDateTime;

import static com.aakbmir.expensetracker.config.BusinessClock.BUSINESS_ZONE;

public class CategoryMapper {

    public static Category mapToCategory(@NotNull @Valid CategoryApiDTO categoryApiDTO) {

        return Category.builder()
                .categoryId(categoryApiDTO.categoryId())
                .categoryGroup(categoryApiDTO.categoryGroup())
                .mainCategory(categoryApiDTO.mainCategory())
                .superCategory(categoryApiDTO.superCategory())
                .category(categoryApiDTO.category())
                .status(CategoryStatus.valueOf(categoryApiDTO.status()))
                .financialType(FinancialType.valueOf(categoryApiDTO.financialType()))
                .budgetAmount(categoryApiDTO.budgetAmount())
                .date(categoryApiDTO.date().toInstant())
                .build();
    }

    public static CategoryApiDTO mapToCategoryApiDTO(@NotNull @Valid Category category) {

        ZonedDateTime date = category.getDate().atZone(BUSINESS_ZONE);
        return CategoryApiDTO.builder()
                .categoryId(category.getCategoryId())
                .categoryGroup(category.getCategoryGroup())
                .mainCategory(category.getMainCategory())
                .superCategory(category.getSuperCategory())
                .category(category.getCategory())
                .status(category.getStatus().name())
                .financialType(category.getFinancialType().name())
                .budgetAmount(category.getBudgetAmount())
                .date(date)
                .build();
    }
}
