package com.aakbmir.expensetracker.usecases.category.bff.dto.builder;

import com.aakbmir.expensetracker.utils.enums.CategoryStatus;
import com.aakbmir.expensetracker.utils.enums.FinancialType;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Builder
public record CategoryResponse(
        Instant date,
        List<FinancialTypeResponse> financialTypes) {

    @Builder
    public record FinancialTypeResponse(
            FinancialType financialType,
            List<MainCategoryResponse> mainCategories) {


        @Builder
        public record MainCategoryResponse(
                String mainCategory,
                List<SuperCategoryResponse> superCategories) {


            @Builder
            public record SuperCategoryResponse(
                    String superCategory,
                    List<CategoryItemResponse> categories,
                    BigDecimal totalBudgetAmount) {


                @Builder
                public record CategoryItemResponse(
                        String categoryGroup,
                        Long categoryId,
                        String categoryName,
                        CategoryStatus status,
                        BigDecimal budgetAmount) {
                }
            }
        }
    }
}
