package com.aakbmir.expensetracker.usecases.income.api.dto;

import com.aakbmir.expensetracker.utils.validators.GstDateTime;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Builder
public record IncomeDTO(
        Long id,
        @NotBlank String category,
        @NotNull @Positive BigDecimal price,
        @GstDateTime @NotNull ZonedDateTime date,
        @NotBlank String note) {
}
