package com.aakbmir.expensetracker.usecases.budget.api.dto;

import com.aakbmir.expensetracker.usecases.category.bff.dto.CategoryApiDTO;
import com.aakbmir.expensetracker.utils.validators.GstDateTime;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Builder
public record BudgetDTO(
        Long budgetId,
        @NotNull CategoryApiDTO categoryApiDTO,
        @NotNull @Positive BigDecimal budgetAmount,
        @GstDateTime @NotNull ZonedDateTime date) {
}
