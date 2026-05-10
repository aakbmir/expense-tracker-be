package com.aakbmir.expensetracker.DTO;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryMasterDTO {

    private Long id;

    private String mainCategory;

    private String oldCategory;

    private String subCategory;

    private String category;

}
