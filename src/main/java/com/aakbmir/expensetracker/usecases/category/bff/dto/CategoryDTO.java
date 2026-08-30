package com.aakbmir.expensetracker.usecases.category.bff.dto;

import lombok.Builder;

@Builder
public record CategoryDTO(
        String name,
        double budget,
        double expense) {
}
