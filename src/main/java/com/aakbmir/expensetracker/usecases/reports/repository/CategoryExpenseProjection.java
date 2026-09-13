package com.aakbmir.expensetracker.usecases.reports.repository;

import java.math.BigDecimal;

public record CategoryExpenseProjection(
        Long categoryId,
        String mainCategory,
        String superCategory,
        String category,
        BigDecimal budgetAmount,
        BigDecimal expenseAmount) {
}