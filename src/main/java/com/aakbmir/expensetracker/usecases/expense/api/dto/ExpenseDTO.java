package com.aakbmir.expensetracker.usecases.expense.api.dto;

import com.aakbmir.expensetracker.usecases.category.bff.dto.CategoryApiDTO;
import com.aakbmir.expensetracker.utils.validators.GstDateTime;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Builder
public record ExpenseDTO(
        Long expenseId,
        CategoryApiDTO categoryApiDTO,
        Long categoryId,
        @GstDateTime @NotNull ZonedDateTime date,
        @NotNull @Positive BigDecimal amount,
        @NotBlank String description,
        int year,
        int month) {
}
