package com.aakbmir.expensetracker.usecases.reports.service;

import com.aakbmir.expensetracker.usecases.category.bff.dto.CategoryApiDTO;
import com.aakbmir.expensetracker.usecases.category.repository.CategoryRepository;
import com.aakbmir.expensetracker.usecases.category.repository.entity.Category;
import com.aakbmir.expensetracker.usecases.category.service.mapper.CategoryMapper;
import com.aakbmir.expensetracker.usecases.expense.api.dto.ExpenseDTO;
import com.aakbmir.expensetracker.usecases.expense.repository.ExpenseRepository;
import com.aakbmir.expensetracker.usecases.expense.repository.entity.Expense;
import com.aakbmir.expensetracker.usecases.expense.service.mapper.ExpenseMapper;
import com.aakbmir.expensetracker.usecases.income.repository.IncomeRepository;
import com.aakbmir.expensetracker.usecases.income.repository.entity.Income;
import com.aakbmir.expensetracker.usecases.reports.api.dto.OverviewRecord;
import com.aakbmir.expensetracker.usecases.savings.repository.SavingsRepository;
import com.aakbmir.expensetracker.usecases.savings.repository.entity.Savings;
import com.aakbmir.expensetracker.utils.enums.FinancialType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportsService {

    private final ExpenseRepository expenseRepository;

    private final CategoryRepository categoryRepository;

    private final IncomeRepository incomeRepository;

    private final SavingsRepository savingsRepository;

    DecimalFormat df = new DecimalFormat("#.##");

    public List<CategoryApiDTO> getDistinctCategories() {
        List<Category> categoryList = categoryRepository.findAllByOrderByMainCategoryAscSuperCategoryAscCategoryAsc();
        return categoryList.stream()
                .map(CategoryMapper::mapToCategoryApiDTO)
                .toList();
    }

    public OverviewRecord calculateAllReportFields(String requestedYear, String requestedMonth) {

        List<Expense> expenseList = expenseRepository.findByMonthAndYear(Integer.parseInt(requestedYear),
                Integer.parseInt(requestedMonth));

        List<Income> incomeList = incomeRepository.findByMonthAndYear(Integer.parseInt(requestedYear),
                Integer.parseInt(requestedMonth));

        List<Savings> savingsList = savingsRepository.findByMonthAndYear(Integer.parseInt(requestedYear),
                Integer.parseInt(requestedMonth));

        List<Category> categoryList = categoryRepository.findAllByOrderByCategoryAsc(Integer.parseInt(requestedYear),
                Integer.parseInt(requestedMonth));

        BigDecimal totalExpenseSpent = expenseList.stream().map(Expense::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalIncome = incomeList.stream().map(Income::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalSavingsDone = savingsList.stream().map(Savings::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalExpenseBudget = categoryList.stream()
                .filter(item -> FinancialType.EXPENSE == item.getFinancialType())
                .map(Category::getBudgetAmount)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalSavingsBudget = categoryList.stream()
                .filter(item -> FinancialType.SAVINGS == item.getFinancialType())
                .map(Category::getBudgetAmount)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);


        return OverviewRecord.builder()
                .totalExpenseBudget(totalExpenseBudget)
                .totalExpenseSpent(totalExpenseSpent)
                .totalSavingsBudget(totalSavingsBudget)
                .totalSavingsDone(totalSavingsDone)
                .totalIncome(totalIncome).build();
    }

    public BigDecimal calculateCumulativeDataForCategoryReportForExpense(String requestedYear, String requestedMonth) {

        List<Expense> expenseList = expenseRepository.findByMonthAndYear(Integer.parseInt(requestedYear),
                Integer.parseInt(requestedMonth));
        return expenseList.stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<ExpenseDTO> findByCategory(String expenseName, String option) {
        if (option.equalsIgnoreCase("Category")) {
            if (expenseName == null || expenseName.equalsIgnoreCase("")) {
                List<Expense> expenseList = expenseRepository.findAllDataByCategory();
                return expenseList.stream()
                        .map(ExpenseMapper::mapToExpenseDTO)
                        .toList();
            } else {
                List<Expense> expenseList = expenseRepository.findCategory(expenseName);
                return expenseList.stream()
                        .map(ExpenseMapper::mapToExpenseDTO)
                        .toList();
            }
        } else if (option.equalsIgnoreCase("Super")) {
            List<Expense> expenseList = expenseRepository.findAllDataByCategory();
            return expenseList.stream().filter(exp -> exp.getCategory().getSuperCategory().equalsIgnoreCase(expenseName))
                    .map(ExpenseMapper::mapToExpenseDTO)
                    .toList();
        } else if (option.equalsIgnoreCase("Parent")) {
            List<Expense> expenseList = expenseRepository.findAllDataByCategory();
            return expenseList.stream().filter(exp -> exp.getCategory().getMainCategory().equalsIgnoreCase(expenseName))
                    .map(ExpenseMapper::mapToExpenseDTO)
                    .toList();
        } else {
            List<Expense> expenseList = expenseRepository.findAllDataByCategory();
            return expenseList.stream()
                    .map(ExpenseMapper::mapToExpenseDTO)
                    .toList();
        }
    }

    private List<String> fetchDistinctSuperCategories(String parentCategoryObj, List<Category> categoryList) {
        return categoryList.stream()
                .filter(row -> parentCategoryObj.equalsIgnoreCase(row.getMainCategory()))
                .map(Category::getSuperCategory)
                .distinct()
                .collect(Collectors.toList());
    }

    private JSONObject calculateExpenseValue(String category,
                                             String subCategory, List<Expense> expenseList) {
        JSONObject json = new JSONObject();

        List<Expense> expenseFilteredList = expenseList.stream()
                .filter(item ->
                        item.getCategory().getCategory().equalsIgnoreCase(category)
                                && item.getCategory().getSuperCategory().equalsIgnoreCase(subCategory))
                .toList();

        if (!expenseFilteredList.isEmpty()) {
            double sum = expenseFilteredList.stream()
                    .mapToDouble(expense -> Double.parseDouble(String.valueOf(expense.getAmount())))
                    .sum();
            json.put("expense", sum);
        } else {
            json.put("expense", 0);
        }
        return json;
    }

}
