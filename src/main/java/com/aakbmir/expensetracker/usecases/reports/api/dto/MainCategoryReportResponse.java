package com.aakbmir.expensetracker.usecases.reports.api.dto;

import java.util.List;

public record MainCategoryReportResponse(
        String mainCategory,
        List<SuperCategoryReportResponse> superCategories) {
}