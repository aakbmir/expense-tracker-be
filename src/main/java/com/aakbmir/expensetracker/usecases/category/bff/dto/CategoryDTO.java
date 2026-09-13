package com.aakbmir.expensetracker.usecases.category.bff.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record CategoryDTO(String name, BigDecimal budget, BigDecimal expense) {}
