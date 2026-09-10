package com.aakbmir.expensetracker.usecases.reports.api.dto;

import com.aakbmir.expensetracker.usecases.budget.api.dto.BudgetDTO;
import com.aakbmir.expensetracker.usecases.category.bff.dto.ParentCategoryDTO;
import com.aakbmir.expensetracker.usecases.expense.api.dto.ExpenseDTO;
import com.aakbmir.expensetracker.usecases.income.repository.entity.Income;
import lombok.Data;

import java.util.List;

@Data
public class GroupReport {

    List<ExpenseDTO> expenses;

    List<BudgetDTO> budgets;

    List<ParentCategoryDTO> parentCategoryDTOList;

    Income income;
}
