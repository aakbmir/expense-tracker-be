package com.aakbmir.expensetracker.usecases.bank.api.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.Instant;

@Builder
public record BankDTO(
        Long id,
        String name,
        Instant date,
        BigDecimal price) {
}
