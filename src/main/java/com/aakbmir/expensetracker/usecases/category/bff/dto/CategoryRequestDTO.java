package com.aakbmir.expensetracker.usecases.category.bff.dto;

import lombok.Builder;

import java.time.Instant;

@Builder
public record CategoryRequestDTO(

        String categoryGroup,
        String mainCategory,
        String subcategory,
        String category,
        Boolean active,
        Instant date) {
}
