package com.aakbmir.expensetracker.usecases.category.bff.dto;

import com.aakbmir.expensetracker.utils.enums.CategoryStatus;
import com.aakbmir.expensetracker.utils.enums.FinancialType;
import com.aakbmir.expensetracker.utils.validators.GstDateTime;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Builder
public record CategoryApiDTO(
        Long categoryId,
        @NotBlank String categoryGroup,
        @NotBlank String mainCategory,
        @NotBlank String superCategory,
        @NotBlank String category,
        @NotBlank String status,
        @GstDateTime ZonedDateTime date,
        @NotBlank String financialType,
        @NotNull @Positive BigDecimal budgetAmount,
        int year,
        int month) {
}
