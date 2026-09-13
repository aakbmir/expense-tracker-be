package com.aakbmir.expensetracker.usecases.reports.api.dto;

import java.math.BigDecimal;

public record CategoryReportResponse(
        Long categoryId,
        String categoryName,
        BigDecimal budgetAmount,
        BigDecimal expenseAmount) {
}