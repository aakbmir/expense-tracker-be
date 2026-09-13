package com.aakbmir.expensetracker.usecases.bank.api.dto;

import java.math.BigDecimal;
import java.time.Instant;
import lombok.Builder;

@Builder
public record BankDTO(Long id, String name, Instant date, BigDecimal price) {}
