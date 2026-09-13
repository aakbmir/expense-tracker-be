package com.aakbmir.expensetracker.usecases.reports.api.dto;

import java.util.List;

public record SuperCategoryReportResponse(
        String superCategory,
        List<CategoryReportResponse> categories) {
}