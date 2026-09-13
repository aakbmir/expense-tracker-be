package com.aakbmir.expensetracker.usecases.reports.service;

import com.aakbmir.expensetracker.usecases.category.bff.dto.CategoryApiDTO;
import com.aakbmir.expensetracker.usecases.category.bff.dto.CategoryDTO;
import com.aakbmir.expensetracker.usecases.category.bff.dto.ParentCategoryDTO;
import com.aakbmir.expensetracker.usecases.category.bff.dto.SuperCategoryDTO;
import com.aakbmir.expensetracker.usecases.category.repository.CategoryRepository;
import com.aakbmir.expensetracker.usecases.category.repository.entity.Category;
import com.aakbmir.expensetracker.usecases.category.service.mapper.CategoryMapper;
import com.aakbmir.expensetracker.usecases.expense.api.dto.ExpenseDTO;
import com.aakbmir.expensetracker.usecases.expense.repository.ExpenseRepository;
import com.aakbmir.expensetracker.usecases.expense.repository.entity.Expense;
import com.aakbmir.expensetracker.usecases.expense.service.mapper.ExpenseMapper;
import com.aakbmir.expensetracker.usecases.income.repository.IncomeRepository;
import com.aakbmir.expensetracker.usecases.income.repository.entity.Income;
import com.aakbmir.expensetracker.usecases.reports.api.dto.CategoryReportResponse;
import com.aakbmir.expensetracker.usecases.reports.api.dto.MainCategoryReportResponse;
import com.aakbmir.expensetracker.usecases.reports.api.dto.OverviewRecord;
import com.aakbmir.expensetracker.usecases.reports.api.dto.SuperCategoryReportResponse;
import com.aakbmir.expensetracker.usecases.reports.repository.CategoryExpenseProjection;
import com.aakbmir.expensetracker.usecases.savings.repository.SavingsRepository;
import com.aakbmir.expensetracker.usecases.savings.repository.entity.Savings;
import com.aakbmir.expensetracker.utils.enums.FinancialType;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportsService {

  private final ExpenseRepository expenseRepository;

  private final CategoryRepository categoryRepository;

  private final IncomeRepository incomeRepository;

  private final SavingsRepository savingsRepository;

  public List<CategoryApiDTO> getDistinctCategories() {
    List<Category> categoryList =
        categoryRepository.findAllByOrderByMainCategoryAscSuperCategoryAscCategoryAsc();
    return categoryList.stream().map(CategoryMapper::mapToCategoryApiDTO).toList();
  }

  public OverviewRecord calculateAllReportFields(String requestedYear, String requestedMonth) {

    List<Expense> expenseList =
        expenseRepository.findByMonthAndYear(
            Integer.parseInt(requestedYear), Integer.parseInt(requestedMonth));

    List<Income> incomeList =
        incomeRepository.findByMonthAndYear(
            Integer.parseInt(requestedYear), Integer.parseInt(requestedMonth));

    List<Savings> savingsList =
        savingsRepository.findByMonthAndYear(
            Integer.parseInt(requestedYear), Integer.parseInt(requestedMonth));

    List<Category> categoryList =
        categoryRepository.findAllByOrderByCategoryAsc(
            Integer.parseInt(requestedYear), Integer.parseInt(requestedMonth));

    BigDecimal totalActualExpenses =
        expenseList.stream().map(Expense::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
    BigDecimal totalIncome =
        incomeList.stream().map(Income::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
    BigDecimal totalActualSavings =
        savingsList.stream().map(Savings::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);

    BigDecimal totalPlannedExpenses =
        categoryList.stream()
            .filter(item -> FinancialType.EXPENSE == item.getFinancialType())
            .map(Category::getBudgetAmount)
            .filter(Objects::nonNull)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

    BigDecimal totalPlannedSavings =
        categoryList.stream()
            .filter(item -> FinancialType.SAVINGS == item.getFinancialType())
            .map(Category::getBudgetAmount)
            .filter(Objects::nonNull)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

    return OverviewRecord.builder()
        .totalPlannedExpenses(totalPlannedExpenses)
        .totalActualExpenses(totalActualExpenses)
        .totalPlannedSavings(totalPlannedSavings)
        .totalActualSavings(totalActualSavings)
        .totalIncome(totalIncome)
        .build();
  }

  public List<ExpenseDTO> findByCategory(String expenseName, String option) {
    List<Expense> expenseList;
    if (option.equalsIgnoreCase("Category")) {

      if (expenseName == null || expenseName.equalsIgnoreCase("")) {
        expenseList = expenseRepository.findAllDataByCategory();
      } else {
        expenseList = expenseRepository.findCategory(expenseName);
      }
      return expenseList.stream().map(ExpenseMapper::mapToExpenseDTO).toList();
    } else if (option.equalsIgnoreCase("Super")) {
      expenseList = expenseRepository.findAllDataByCategory();
      return expenseList.stream()
          .filter(exp -> exp.getCategory().getSuperCategory().equalsIgnoreCase(expenseName))
          .map(ExpenseMapper::mapToExpenseDTO)
          .toList();
    } else if (option.equalsIgnoreCase("Parent")) {
      expenseList = expenseRepository.findAllDataByCategory();
      return expenseList.stream()
          .filter(exp -> exp.getCategory().getMainCategory().equalsIgnoreCase(expenseName))
          .map(ExpenseMapper::mapToExpenseDTO)
          .toList();
    } else {
      expenseList = expenseRepository.findAllDataByCategory();
      return expenseList.stream().map(ExpenseMapper::mapToExpenseDTO).toList();
    }
  }

  public List<MainCategoryReportResponse> getExpenseCategories(String year, String month) {
    List<CategoryExpenseProjection> rows =
        categoryRepository.findMonthlyCategoryExpenses(
            Integer.parseInt(year), Integer.parseInt(month));

    Map<String, Map<String, List<CategoryExpenseProjection>>> grouped =
        rows.stream()
            .collect(
                Collectors.groupingBy(
                    CategoryExpenseProjection::mainCategory,
                    LinkedHashMap::new,
                    Collectors.groupingBy(
                        CategoryExpenseProjection::superCategory,
                        LinkedHashMap::new,
                        Collectors.toList())));

    return grouped.entrySet().stream()
        .map(
            mainEntry -> {
              List<SuperCategoryReportResponse> superCategories =
                  mainEntry.getValue().entrySet().stream()
                      .map(
                          superEntry -> {
                            List<CategoryReportResponse> categories =
                                superEntry.getValue().stream()
                                    .map(
                                        row ->
                                            new CategoryReportResponse(
                                                row.categoryId(),
                                                row.category(),
                                                row.budgetAmount(),
                                                row.expenseAmount()))
                                    .toList();

                            return new SuperCategoryReportResponse(superEntry.getKey(), categories);
                          })
                      .toList();

              return new MainCategoryReportResponse(mainEntry.getKey(), superCategories);
            })
        .toList();
  }

  public List<ParentCategoryDTO> calculateDataForCategoryReport(
      String requestedYear, String requestedMonth) {
    List<Category> categoryList =
        categoryRepository.findAllByOrderByCategoryAsc(
            Integer.parseInt(requestedYear), Integer.parseInt(requestedMonth));
    Collections.sort(categoryList);

    List<Expense> expenseList;
    if (requestedMonth.equalsIgnoreCase("All")) {
      expenseList = expenseRepository.findByYear(Integer.parseInt(requestedYear));
    } else {
      expenseList =
          expenseRepository.findByMonthAndYear(
              Integer.parseInt(requestedYear), Integer.parseInt(requestedMonth));
    }

    List<String> distinctParentCatList =
        categoryList.stream().map(Category::getMainCategory).distinct().toList();

    List<ParentCategoryDTO> parentCategoryDTOList = new ArrayList<>();
    for (String distinctParentCatObj : distinctParentCatList) {
      if (distinctParentCatObj.equalsIgnoreCase("Investments")) {
        log.info("ignored parent Category");
      } else {
        ParentCategoryDTO parentCategoryDTO = new ParentCategoryDTO();
        parentCategoryDTO.setName(distinctParentCatObj);
        List<String> distinctSuperCatList =
            fetchDistinctSuperCategories(distinctParentCatObj, categoryList);
        List<SuperCategoryDTO> superCatDtoList = new ArrayList<>();
        BigDecimal parentCatBudget = new BigDecimal("0.0");
        BigDecimal parentCatExpense = new BigDecimal("0.0");
        for (String distinctSuperCatObj : distinctSuperCatList) {
          SuperCategoryDTO superCategoryDTO = new SuperCategoryDTO();
          superCategoryDTO.setName(distinctSuperCatObj);
          List<Category> filteredCategoryList =
              categoryList.stream()
                  .filter(item -> item.getSuperCategory().equalsIgnoreCase(distinctSuperCatObj))
                  .toList();
          List<CategoryDTO> catDtoList = new ArrayList<>();
          BigDecimal superCatBudget = new BigDecimal("0.0");
          BigDecimal superCatExpense = new BigDecimal("0.0");
          for (Category distinctCatObj : filteredCategoryList) {

            CategoryDTO categoryDTO =
                CategoryDTO.builder()
                    .name(distinctCatObj.getCategory())
                    .expense(
                        new BigDecimal(
                            calculateExpenseValue(
                                    distinctCatObj.getCategory(),
                                    distinctCatObj.getSuperCategory(),
                                    expenseList)
                                .get("expense")
                                .toString()))
                    .budget(distinctCatObj.getBudgetAmount())
                    .build();
            superCatExpense = superCatExpense.add(categoryDTO.expense());
            superCatBudget = superCatBudget.add(categoryDTO.budget());
            catDtoList.add(categoryDTO);
          }

          superCategoryDTO.setCategoryDtoList(catDtoList);
          superCategoryDTO.setBudget(superCatBudget);
          superCategoryDTO.setExpense(superCatExpense);
          parentCatBudget = parentCatBudget.add(superCategoryDTO.getBudget());
          parentCatExpense = parentCatExpense.add(superCategoryDTO.getExpense());
          superCatDtoList.add(superCategoryDTO);
        }
        parentCategoryDTO.setBudget(parentCatBudget);
        parentCategoryDTO.setExpense(parentCatExpense);
        parentCategoryDTO.setSubCategoryDtoList(superCatDtoList);
        parentCategoryDTOList.add(parentCategoryDTO);
      }
    }
    return parentCategoryDTOList;
  }

  private List<String> fetchDistinctSuperCategories(
      String parentCategoryObj, List<Category> categoryList) {
    return categoryList.stream()
        .filter(row -> parentCategoryObj.equalsIgnoreCase(row.getMainCategory()))
        .map(Category::getSuperCategory)
        .distinct()
        .collect(Collectors.toList());
  }

  private JSONObject calculateExpenseValue(
      String category, String subCategory, List<Expense> expenseList) {
    JSONObject json = new JSONObject();

    List<Expense> expenseFilteredList =
        expenseList.stream()
            .filter(
                item ->
                    item.getCategory().getCategory().equalsIgnoreCase(category)
                        && item.getCategory().getSuperCategory().equalsIgnoreCase(subCategory))
            .toList();

    if (!expenseFilteredList.isEmpty()) {
      double sum =
          expenseFilteredList.stream()
              .mapToDouble(expense -> Double.parseDouble(String.valueOf(expense.getAmount())))
              .sum();
      json.put("expense", sum);
    } else {
      json.put("expense", 0);
    }
    return json;
  }
}
