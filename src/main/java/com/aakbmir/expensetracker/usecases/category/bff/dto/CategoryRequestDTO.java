package com.aakbmir.expensetracker.usecases.category.bff.dto;

import java.time.Instant;
import lombok.Builder;

@Builder
public record CategoryRequestDTO(
    String categoryGroup,
    String mainCategory,
    String subcategory,
    String category,
    Boolean active,
    Instant date) {}
