package com.aakbmir.expensetracker.usecases.savings.service.mapper;

import com.aakbmir.expensetracker.usecases.category.repository.entity.Category;
import com.aakbmir.expensetracker.usecases.savings.api.dto.SavingsDTO;
import com.aakbmir.expensetracker.usecases.savings.repository.entity.Savings;

import java.time.ZonedDateTime;

import static com.aakbmir.expensetracker.config.BusinessClock.BUSINESS_ZONE;
import static com.aakbmir.expensetracker.usecases.category.service.mapper.CategoryMapper.mapToCategoryApiDTO;

public class SavingsMapper {

    public static Savings mapToSavings(SavingsDTO savingsDTO, Category category) {

        return Savings.builder()
                .savingsId(savingsDTO.savingsId())
                .category(category)
                .date(savingsDTO.date().toInstant())
                .amount(savingsDTO.amount())
                .description(savingsDTO.description())
                .build();
    }

    public static SavingsDTO mapToSavingsDTO(Savings savings) {

        ZonedDateTime date = savings.getDate().atZone(BUSINESS_ZONE);
        return SavingsDTO.builder()
                .savingsId(savings.getSavingsId())
                .categoryApiDTO(mapToCategoryApiDTO(savings.getCategory()))
                .date(date)
                .amount(savings.getAmount())
                .description(savings.getDescription())
                .build();
    }
}
