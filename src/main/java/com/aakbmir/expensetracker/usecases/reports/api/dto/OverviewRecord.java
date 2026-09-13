package com.aakbmir.expensetracker.usecases.reports.api.dto;

import java.math.BigDecimal;
import lombok.Builder;

@Builder
public record OverviewRecord(
    BigDecimal totalIncome,
    BigDecimal totalActualExpenses,
    BigDecimal totalPlannedExpenses,
    BigDecimal totalPlannedSavings,
    BigDecimal totalActualSavings) {}
