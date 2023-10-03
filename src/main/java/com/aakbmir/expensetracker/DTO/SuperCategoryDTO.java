package com.aakbmir.expensetracker.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SuperCategoryDTO {

    private String name;

    private List<CategoryDTO> categoryDtoList;

    private String completed;

    private double budget;

    private double expense;

    private boolean expanded = false;
}
