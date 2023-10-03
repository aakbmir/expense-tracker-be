package com.aakbmir.expensetracker.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParentCategoryDTO {

    private String name;

    private List<SuperCategoryDTO> superCategoryDtoList;

    private String completed;

    private double budget;

    private double expense;

    private boolean expanded = true;
}
