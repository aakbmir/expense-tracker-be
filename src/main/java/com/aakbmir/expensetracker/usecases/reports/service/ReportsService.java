package com.aakbmir.expensetracker.usecases.reports.service;

import com.aakbmir.expensetracker.usecases.bank.repository.BankRepository;
import com.aakbmir.expensetracker.usecases.budget.repository.BudgetRepository;
import com.aakbmir.expensetracker.usecases.budget.repository.entity.Budget;
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
import com.aakbmir.expensetracker.usecases.reports.repository.ReportsRepository;
import com.aakbmir.expensetracker.utils.CommonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportsService {

    private final BudgetRepository budgetRepository;

    private final ReportsRepository reportsRepository;

    private final ExpenseRepository expenseRepository;

    private final IncomeRepository incomeRepository;

    private final CategoryRepository categoryRepository;

    private final BankRepository bankRepository;

    private final CommonUtils commonUtils;

    DecimalFormat df = new DecimalFormat("#.##");

    public List<CategoryApiDTO> getDistinctCategories() {
        List<Category> categoryList = categoryRepository.findAllByOrderByMainCategoryAscSuperCategoryAscCategoryAsc();
        return categoryList.stream()
                .map(CategoryMapper::mapToCategoryApiDTO)
                .toList();
    }


    public JSONArray calculateDataForOverviewReport(String requestedYear, String requestedMonth) {

        List<Budget> totalBudget;
        List<Expense> totalExpense;
        if (requestedMonth.equalsIgnoreCase("All")) {
            totalBudget = budgetRepository.findByYear(Integer.parseInt(requestedYear));
            totalExpense = expenseRepository.findByYear(Integer.parseInt(requestedYear));
        } else {
            totalBudget = budgetRepository.findByMonthAndYear(Integer.parseInt(requestedYear), Integer.parseInt(requestedMonth));
            totalExpense = expenseRepository.findByMonthAndYear(Integer.parseInt(requestedYear), Integer.parseInt(requestedMonth));
        }
        List<String> categoryList = commonUtils.fetchDistinctSubCategories();

        JSONArray jsonArray = new JSONArray();
        for (String cat : categoryList) {
            JSONObject json = new JSONObject();

            json.put("subCategory", cat);

            for (Budget budgetObj : totalBudget) {
                if (budgetObj.getCategory().getSuperCategory().equalsIgnoreCase(cat)) {
                    if (json.has("budget")) {
                        BigDecimal budObj;
                        if (json.get("budget") instanceof Integer) {
                            budObj = new BigDecimal(String.valueOf(json.get("budget")));
                        } else if (json.get("budget") instanceof Double) {
                            budObj = new BigDecimal(String.valueOf(json.get("budget")));
                        } else if (json.get("budget") instanceof Long) {
                            budObj = new BigDecimal(String.valueOf(json.get("budget")));
                        } else {
                            budObj = new BigDecimal(String.valueOf(json.get("budget")));
                        }
                        budObj = budObj.add(budgetObj.getBudgetAmount());
                        String formattedValue = df.format(budObj);
                        double result = Double.parseDouble(formattedValue);
                        json.put("budget", result);
                    } else {
                        String formattedValue = df.format(budgetObj.getBudgetAmount());
                        double result = Double.parseDouble(formattedValue);
                        json.put("budget", result);
                    }
                }
            }
            for (Expense expenseObj : totalExpense) {
                if (expenseObj.getCategory().getSuperCategory().equalsIgnoreCase(cat)) {
                    if (json.has("expense")) {
                        BigDecimal expObj;
                        if (json.get("expense") instanceof Integer) {
                            expObj = new BigDecimal(String.valueOf(json.get("expense")));
                        } else if (json.get("expense") instanceof Double) {
                            expObj = new BigDecimal(String.valueOf(json.get("expense")));
                        } else if (json.get("expense") instanceof Long) {
                            expObj = new BigDecimal(String.valueOf(json.get("expense")));
                        } else {
                            expObj = new BigDecimal(String.valueOf(json.get("expense")));
                        }
                        expObj = expObj.add(expenseObj.getAmount());
                        String formattedValue = df.format(expObj);
                        double result = Double.parseDouble(formattedValue);
                        json.put("expense", result);
                    } else {
                        String formattedValue = df.format(expenseObj.getAmount());
                        double result = Double.parseDouble(formattedValue);
                        json.put("expense", result);
                    }
                }
            }
            if (!json.has("expense")) {
                json.put("expense", 0.0);
            }

            if (json.has("budget") && json.has("expense")) {
                String formattedValue = df.format((double) json.get("budget") - (double) json.get("expense"));
                double result = Double.parseDouble(formattedValue);
                json.put("deviate", result);
            } else {
                json.put("deviate", 0);
                json.put("budget", 0);
            }
            jsonArray.put(json);
        }
        return jsonArray;
    }

/*
    public List<Expense> fetchCategoryReportDetails(String category, Integer year, Integer month) {
        return reportsRepository.findCategoryByMonthAndYear(year, month, category);
    }

    public JSONArray calculateDataForSuperCategoryReport(int year, int month) {
        List<String> categoryList = commonUtils.fetchDistinctSubCategories();
        Collections.sort(categoryList);
        List<Budget> totalBudget = budgetRepository.findByMonthAndYear(year, month);
        List<Expense> totalExpense = expenseRepository.findByMonthAndYear(year, month);

        DecimalFormat df = new DecimalFormat("#.##");

        JSONArray jsonArray = new JSONArray();
        for (String cat : categoryList) {
            JSONObject json = new JSONObject();
            json.put("superCategory", cat);
            for (Budget budgetObj : totalBudget) {
                if (budgetObj.getSubCategory().equalsIgnoreCase(cat)) {
                    if (json.has("budget")) {
                        BigDecimal budObj;
                        if (json.get("budget") instanceof Integer) {
                            budObj = new BigDecimal(String.valueOf(json.get("budget")));
                        } else if (json.get("budget") instanceof Double) {
                            budObj = new BigDecimal(String.valueOf(json.get("budget")));
                        } else if (json.get("budget") instanceof Long) {
                            budObj = new BigDecimal(String.valueOf(json.get("budget")));
                        } else {
                            budObj = new BigDecimal(String.valueOf(json.get("budget")));
                        }
                        budObj = budObj.add(budgetObj.getPrice());
                        String formattedValue = df.format(budObj);
                        double result = Double.parseDouble(formattedValue);
                        json.put("budget", result);
                    } else {
                        String formattedValue = df.format(budgetObj.getPrice());
                        double result = Double.parseDouble(formattedValue);
                        json.put("budget", result);
                    }
                }
            }
            for (Expense expenseObj : totalExpense) {
                if (expenseObj.getSubCategory().equalsIgnoreCase(cat)) {
                    if (json.has("expense")) {
                        BigDecimal expObj;
                        if (json.get("expense") instanceof Integer) {
                            expObj = new BigDecimal(String.valueOf(json.get("expense")));
                        } else if (json.get("expense") instanceof Double) {
                            expObj = new BigDecimal(String.valueOf(json.get("expense")));
                        } else if (json.get("expense") instanceof Long) {
                            expObj = new BigDecimal(String.valueOf(json.get("expense")));
                        } else {
                            expObj = new BigDecimal(String.valueOf(json.get("expense")));
                        }
                        expObj = expObj.add(expenseObj.getPrice());
                        String formattedValue = df.format(expObj);
                        double result = Double.parseDouble(formattedValue);
                        json.put("expense", result);
                    } else {
                        String formattedValue = df.format(expenseObj.getPrice());
                        double result = Double.parseDouble(formattedValue);
                        json.put("expense", result);
                    }
                }
            }
            if (!json.has("expense")) {
                json.put("expense", 0.0);
            }

            String formattedValue = df.format((double) json.get("budget") - (double) json.get("expense"));
            double result = Double.parseDouble(formattedValue);
            json.put("deviate", result);
            jsonArray.put(json);
        }
        return jsonArray;
    }

    public List<Expense> fetchSuperCategoryReportDetails(String superCategory, Integer year, Integer month) {
        return reportsRepository.findSubCategoryByMonthAndYear(year, month, superCategory);
    }





    public ArrayList<?> calculateDataForSavingsReport() {
        List<Expense> expenseList;
        expenseList = expenseRepository.fetchMainCategoryExpense("Savings");
        List<Object[]> expenseTracker = expenseRepository.fetchSumByYearAndMonth();
        List<Object[]> budgetTracker = budgetRepository.fetchSumByYearAndMonth();
        Map<String, JSONObject> monthlyTotalsMap = new TreeMap<>(Collections.reverseOrder());
        DecimalFormat df = new DecimalFormat("#.##");

        for (Expense expense : expenseList) {
            String monthYear = CommonUtils.getMonthYear(expense.getDate());
            if (!monthlyTotalsMap.containsKey(monthYear)) {
                JSONObject json = new JSONObject();
                json.put("month", monthYear).put("totalAccount", 0.0).put("totalInvest", 0.0).put("budgetAmount", 0.0);
                monthlyTotalsMap.put(monthYear, json);
            }
            JSONObject monthlyTotal = monthlyTotalsMap.get(monthYear);

            for (Object[] exx : expenseTracker) {
                if (exx[0].toString().equalsIgnoreCase(monthYear)) {
                    double budgetAmount = 0;
                    for (Object[] bud : budgetTracker) {
                        if (bud[0].toString().equalsIgnoreCase(monthYear)) {
                            budgetAmount = (double) bud[1] - (double) exx[1];
                            break;
                        }
                    }
                    monthlyTotal.put("budgetAmount", budgetAmount);
                    break;
                }
            }

            if (expense.getCategory().equalsIgnoreCase("NRE") || expense.getCategory().equalsIgnoreCase("FRE")) {
                BigDecimal expObj = new BigDecimal(String.valueOf(monthlyTotal.get("totalAccount")));
                expObj = expObj.add(expense.getPrice());
                String formattedValue = df.format(expObj);
                double result = Double.parseDouble(formattedValue);
                monthlyTotal.put("totalAccount", result);
            } else {
                BigDecimal expObj = new BigDecimal(String.valueOf(monthlyTotal.get("totalInvest")));
                expObj = expObj.add(expense.getPrice());
                String formattedValue = df.format(expObj);
                double result = Double.parseDouble(formattedValue);
                monthlyTotal.put("totalInvest", result);
            }
        }

        return new ArrayList<>(monthlyTotalsMap.values());
    }

    public ArrayList calculateDataForBankReport() {
        List<Bank> bankRecords = bankRepository.findAll();
        DecimalFormat df = new DecimalFormat("#.##");
        Map<String, JSONObject> monthlyTotalsMap = new TreeMap<>(Collections.reverseOrder());
        BigDecimal hdfcObj = new BigDecimal("0");
        BigDecimal nbdObj = new BigDecimal("0");
        BigDecimal mshObj = new BigDecimal("0");
        for (Bank bankRecordObj : bankRecords) {
            String monthYear = CommonUtils.getMonthYear(bankRecordObj.getDate());
            if (!monthlyTotalsMap.containsKey(monthYear)) {
                JSONObject json = new JSONObject();
                json.put("month", monthYear).put("ENBD", 0.0).put("HDFC", 0.0).put("Mashreq", 0.0);
                monthlyTotalsMap.put(monthYear, json);
            }
            JSONObject monthlyTotal = monthlyTotalsMap.get(monthYear);

            String formattedValue = null;
            if (bankRecordObj.getName().equalsIgnoreCase("HDFC")) {
                hdfcObj = hdfcObj.add(bankRecordObj.getPrice());
                formattedValue = df.format(hdfcObj);
            } else if (bankRecordObj.getName().equalsIgnoreCase("ENBD")) {
                nbdObj = nbdObj.add(bankRecordObj.getPrice());
                formattedValue = df.format(nbdObj);
            } else if (bankRecordObj.getName().equalsIgnoreCase("Mashreq")) {
                mshObj = mshObj.add(bankRecordObj.getPrice());
                formattedValue = df.format(mshObj);
            }

            assert formattedValue != null;
            BigDecimal result = new BigDecimal(formattedValue);
            monthlyTotal.put(bankRecordObj.getName(), result);
            System.out.println(monthlyTotalsMap.values());

        }
        return new ArrayList<>(monthlyTotalsMap.values());
    }
*/

    public List<ExpenseDTO> findByCategory(String expenseName, String option) {
        if (option.equalsIgnoreCase("Category")) {
            if (expenseName == null || expenseName.equalsIgnoreCase("")) {
                List<Expense> expenseList = expenseRepository.findAllByCategory();
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
            List<Expense> expenseList = expenseRepository.findAllByCategory();
            return expenseList.stream().filter(exp -> exp.getCategory().getSuperCategory().equalsIgnoreCase(expenseName))
                    .map(ExpenseMapper::mapToExpenseDTO)
                    .toList();
        } else if (option.equalsIgnoreCase("Parent")) {
            List<Expense> expenseList = expenseRepository.findAllByCategory();
            return expenseList.stream().filter(exp -> exp.getCategory().getMainCategory().equalsIgnoreCase(expenseName))
                    .map(ExpenseMapper::mapToExpenseDTO)
                    .toList();
        } else {
            List<Expense> expenseList = expenseRepository.findAllByCategory();
            return expenseList.stream()
                    .map(ExpenseMapper::mapToExpenseDTO)
                    .toList();
        }
    }

    public ArrayList<?> calculateDataForTrendsReport() {
        List<Budget> budgets = budgetRepository.findAllByOrderByDateAsc();
        List<Expense> expenses = expenseRepository.findAllByOrderByDateAsc();
        List<Income> incomes = incomeRepository.findAllByOrderByDateAsc();
        Map<String, JSONObject> monthlyTotalsMap = new TreeMap<>(Collections.reverseOrder());
        DecimalFormat df = new DecimalFormat("#.##");

        for (Expense expense : expenses) {
            String monthYear = CommonUtils.getMonthYear(expense.getDate());
            if (!monthlyTotalsMap.containsKey(monthYear)) {
                JSONObject json = new JSONObject();
                json.put("month", monthYear).put("totalExpense", 0.0).put("totalBudget", 0.0).put("totalIncome", 0.0);
                monthlyTotalsMap.put(monthYear, json);
            }
            JSONObject monthlyTotal = monthlyTotalsMap.get(monthYear);
            BigDecimal expObj = new BigDecimal(String.valueOf(monthlyTotal.get("totalExpense")));
            expObj = expObj.add(expense.getAmount());
            String formattedValue = df.format(expObj);
            double result = Double.parseDouble(formattedValue);
            monthlyTotal.put("totalExpense", result);
        }

        for (Income income : incomes) {
            String monthYear = CommonUtils.getMonthYear(income.getDate());
            if (!monthlyTotalsMap.containsKey(monthYear)) {
                JSONObject json = new JSONObject();
                json.put("month", monthYear).put("totalIncome", 0.0).put("totalBudget", 0.0).put("totalIncome", 0.0);
                monthlyTotalsMap.put(monthYear, json);
            }
            JSONObject monthlyTotal = monthlyTotalsMap.get(monthYear);
            BigDecimal incomeObj = new BigDecimal(String.valueOf(monthlyTotal.get("totalIncome")));
            incomeObj = incomeObj.add(income.getPrice());
            String formattedValue = df.format(incomeObj);
            double result = Double.parseDouble(formattedValue);
            monthlyTotal.put("totalIncome", result);
        }

        for (Budget budget : budgets) {
            String monthYear = CommonUtils.getMonthYear(budget.getDate());
            if (!monthlyTotalsMap.containsKey(monthYear)) {
                JSONObject json = new JSONObject();
                json.put("month", monthYear).put("totalExpense", 0.0).put("totalBudget", 0.0).put("totalIncome", 0.0);
                monthlyTotalsMap.put(monthYear, json);
            }
            JSONObject monthlyTotal = monthlyTotalsMap.get(monthYear);
            BigDecimal expObj = new BigDecimal(String.valueOf(monthlyTotal.get("totalBudget")));
            expObj = expObj.add(budget.getBudgetAmount());
            String formattedValue = df.format(expObj);
            double result = Double.parseDouble(formattedValue);
            monthlyTotal.put("totalBudget", result);
        }

        return new ArrayList<>(monthlyTotalsMap.values());
    }

    public List<ParentCategoryDTO> calculateDataForCategoryReport(String requestedYear, String requestedMonth) {
        List<Category> categoryList = commonUtils.fetchAllCategories(true, Integer.parseInt(requestedYear), Integer.parseInt(requestedMonth));
        Collections.sort(categoryList);

        List<Budget> budgetList;
        List<Expense> expenseList;
        if (requestedMonth.equalsIgnoreCase("All")) {
            budgetList = budgetRepository.findByYear(Integer.parseInt(requestedYear));
            expenseList = expenseRepository.findByYear(Integer.parseInt(requestedYear));
        } else {
            budgetList = budgetRepository.findByMonthAndYear(Integer.parseInt(requestedYear), Integer.parseInt(requestedMonth));
            expenseList = expenseRepository.findByMonthAndYear(Integer.parseInt(requestedYear), Integer.parseInt(requestedMonth));
        }

        List<String> distinctParentCatList = categoryList.stream().map(Category::getMainCategory).distinct().toList();

        List<ParentCategoryDTO> parentCategoryDTOList = new ArrayList<>();
        for (String distinctParentCatObj : distinctParentCatList) {
            if (distinctParentCatObj.equalsIgnoreCase("Investments")) {
                log.info("ignored parent Category");
            } else {
                ParentCategoryDTO parentCategoryDTO = new ParentCategoryDTO();
                parentCategoryDTO.setName(distinctParentCatObj);
                List<String> distinctSuperCatList = fetchDistinctSuperCategories(distinctParentCatObj, categoryList);
                List<SuperCategoryDTO> superCatDtoList = new ArrayList<>();
                double parentCatBudget = 0;
                double parentCatExpense = 0;
                for (String distinctSuperCatObj : distinctSuperCatList) {
                    SuperCategoryDTO superCategoryDTO = new SuperCategoryDTO();
                    superCategoryDTO.setName(distinctSuperCatObj);
                    List<Category> filteredCategoryList = categoryList.stream().filter(item -> item.getSuperCategory().equalsIgnoreCase(distinctSuperCatObj)).toList();
                    List<CategoryDTO> catDtoList = new ArrayList<>();
                    double superCatBudget = 0;
                    double superCatExpense = 0;
                    for (Category distinctCatObj : filteredCategoryList) {


                        CategoryDTO categoryDTO = CategoryDTO.builder()
                                .name(distinctCatObj.getCategory())
                                .expense(Double.parseDouble(calculateExpenseValue(distinctCatObj.getCategory(),
                                        distinctCatObj.getSuperCategory(), expenseList).get("expense").toString()))
                                .budget(calculateBudgetValue(distinctCatObj.getCategory(), budgetList)).build();
                        superCatExpense = superCatExpense + categoryDTO.expense();
                        superCatBudget = superCatBudget + categoryDTO.budget();
                        catDtoList.add(categoryDTO);
                    }

                    superCategoryDTO.setCategoryDtoList(catDtoList);
                    superCategoryDTO.setBudget(superCatBudget);
                    superCategoryDTO.setExpense(superCatExpense);
                    parentCatBudget = parentCatBudget + superCategoryDTO.getBudget();
                    parentCatExpense = parentCatExpense + superCategoryDTO.getExpense();
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

    private double calculateBudgetValue(String category, List<Budget> budgetList) {
        double sum;
        List<Budget> expenseFilteredList = budgetList.stream().filter(item -> item.getCategory().getCategory().equalsIgnoreCase(category)).toList();
        sum = expenseFilteredList.stream()
                .mapToDouble(budget -> Double.parseDouble(String.valueOf(budget.getBudgetAmount())))
                .sum();
        return sum;
    }

}
