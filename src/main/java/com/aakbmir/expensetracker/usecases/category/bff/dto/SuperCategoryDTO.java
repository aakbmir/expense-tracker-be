package com.aakbmir.expensetracker.usecases.category.bff.dto;

import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SuperCategoryDTO {

  private String name;

  private List<CategoryDTO> categoryDtoList;

  private BigDecimal budget;

  private BigDecimal expense;

  @Builder.Default private boolean expanded = false;
}
