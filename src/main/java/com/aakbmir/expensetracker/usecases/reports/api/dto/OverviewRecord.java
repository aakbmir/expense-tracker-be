package com.aakbmir.expensetracker.usecases.reports.api.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
public record OverviewRecord(
        BigDecimal totalExpenseBudget,
        BigDecimal totalExpenseSpent,
        BigDecimal totalSavingsBudget,
        BigDecimal totalSavingsDone,
        BigDecimal totalIncome) {
}
