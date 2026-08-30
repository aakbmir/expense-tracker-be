package com.aakbmir.expensetracker.usecases.savings.service.mapper;

public class SavingsMapper {

/*    public Savings mapToExpense(ExpenseDTO expenseDTO) {

        return Savings.builder()
                .expenseId(expenseDTO.expenseId())
                .category(mapToCategory(expenseDTO.categoryApiDTO()))
                .date(expenseDTO.date().toInstant())
                .amount(expenseDTO.amount())
                .description(expenseDTO.description())
                .miscellaneous(expenseDTO.miscellaneous())
                .build();
    }

    public ExpenseDTO mapToExpenseDTO(Savings savings) {

        ZonedDateTime date = savings.getDate().atZone(BUSINESS_ZONE);
        return ExpenseDTO.builder()
                .expenseId(savings.getExpenseId())
                .categoryApiDTO(mapToCategoryApiDTO(savings.getCategory()))
                .date(date)
                .amount(savings.getAmount())
                .description(savings.getDescription())
                .miscellaneous(savings.getMiscellaneous())
                .build();
    }*/
}
