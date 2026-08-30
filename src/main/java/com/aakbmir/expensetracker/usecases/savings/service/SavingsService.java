package com.aakbmir.expensetracker.usecases.savings.service;

import org.springframework.stereotype.Service;

@Service
public class SavingsService {

/*    @Autowired
    ExpenseRepository expenseRepository;

    @Autowired
    CommonUtils commonUtils;

    public Expense saveExpense(ExpenseDTO expense) {
        Category category = null;

        ZonedDateTime zdt =
                expense.date().atZone(ZoneId.of("Asia/Dubai"));

        int month = zdt.getMonthValue();
        int year = zdt.getYear();

        for (Category cat : commonUtils.fetchAllCategories(year, month)) {
            if (expense.getCategory().equalsIgnoreCase(cat.getCategory())) {
                category = cat;
                break;
            }
        }
        expense.setMainCategory(category.getMainCategory());
        expense.setSubCategory(category.getSubCategory());
        return expenseRepository.save(expense);
    }

    public List<Expense> findByCategory(String expense) {
        if (expense == null || expense.equalsIgnoreCase("")) {
            return expenseRepository.findAllByCategory();
        } else {
            return expenseRepository.findCategory(expense);
        }
    }

    public Optional<Expense> findById(Long id) {
        return expenseRepository.findById(id);
    }

    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }

    public List<Expense> findByMonthAndYear(int year, int month) {
        return expenseRepository.findByMonthAndYear(year, month);
    }*/
}