package com.aakbmir.expensetracker.usecases.category.bff.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SuperCategoryDTO {

    private String name;

    private List<CategoryDTO> categoryDtoList;

    private double budget;

    private double expense;

    @Builder.Default
    private boolean expanded = false;
}
