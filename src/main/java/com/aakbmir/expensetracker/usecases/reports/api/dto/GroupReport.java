package com.aakbmir.expensetracker.usecases.reports.api.dto;

import com.aakbmir.expensetracker.usecases.category.bff.dto.ParentCategoryDTO;
import com.aakbmir.expensetracker.usecases.income.repository.entity.Income;
import java.util.List;
import lombok.Data;

@Data
public class GroupReport {

  List<Object> expenses;

  List<ParentCategoryDTO> parentCategoryDTOList;

  Income income;
}
