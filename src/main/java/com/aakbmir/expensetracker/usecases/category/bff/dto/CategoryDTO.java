package com.aakbmir.expensetracker.usecases.category.bff.dto;

import java.math.BigDecimal;
import lombok.Builder;

@Builder
public record CategoryDTO(String name, BigDecimal budget, BigDecimal expense) {}
