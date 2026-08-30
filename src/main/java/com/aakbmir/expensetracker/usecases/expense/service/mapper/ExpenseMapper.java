package com.aakbmir.expensetracker.usecases.expense.service.mapper;

import com.aakbmir.expensetracker.usecases.category.repository.entity.Category;
import com.aakbmir.expensetracker.usecases.expense.api.dto.ExpenseDTO;
import com.aakbmir.expensetracker.usecases.expense.repository.entity.Expense;

import java.time.ZonedDateTime;

import static com.aakbmir.expensetracker.config.BusinessClock.BUSINESS_ZONE;
import static com.aakbmir.expensetracker.usecases.category.service.mapper.CategoryMapper.mapToCategoryApiDTO;

public class ExpenseMapper {

    public static Expense mapToExpense(ExpenseDTO expenseDTO, Category category) {

        return Expense.builder()
                .expenseId(expenseDTO.expenseId())
                .category(category)
                .date(expenseDTO.date().toInstant())
                .amount(expenseDTO.amount())
                .description(expenseDTO.description())
                .build();
    }

    public static ExpenseDTO mapToExpenseDTO(Expense expense) {

        ZonedDateTime date = expense.getDate().atZone(BUSINESS_ZONE);
        return ExpenseDTO.builder()
                .expenseId(expense.getExpenseId())
                .categoryApiDTO(mapToCategoryApiDTO(expense.getCategory()))
                .date(date)
                .amount(expense.getAmount())
                .description(expense.getDescription())
                .build();
    }
}
