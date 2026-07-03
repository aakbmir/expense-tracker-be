package com.aakbmir.expensetracker.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryMasterDTO {

    @NotNull
    private Long id;

    @NotBlank
    private String mainCategory;

    @NotBlank
    private String oldCategory;

    @NotBlank
    private String subCategory;

    @NotBlank
    private String category;

    @NotNull
    private int year;

    @NotNull
    private int month;
}
