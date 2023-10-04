package com.aakbmir.expensetracker.service;

import com.aakbmir.expensetracker.DTO.CategoryDTO;
import com.aakbmir.expensetracker.DTO.ParentCategoryDTO;
import com.aakbmir.expensetracker.DTO.SuperCategoryDTO;
import com.aakbmir.expensetracker.entity.Budget;
import com.aakbmir.expensetracker.entity.Category;
import com.aakbmir.expensetracker.entity.Expense;
import com.aakbmir.expensetracker.repository.BudgetRepository;
import com.aakbmir.expensetracker.repository.ExpenseRepository;
import com.aakbmir.expensetracker.repository.ReportsRepository;
import com.aakbmir.expensetracker.utils.CommonUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReportsService {

    @Autowired
    BudgetRepository budgetRepository;

    @Autowired
    ReportsRepository reportsRepository;

    @Autowired
    ExpenseRepository expenseRepository;

    @Autowired
    CommonUtils commonUtils;

    public JSONArray calculateDataForOverviewReport(String requestedYear, String requestedMonth) {

        List<Budget> totalBudget = null;
        List<Expense> totalExpense = null;
        if (requestedMonth.equalsIgnoreCase ("All")) {
            totalBudget = budgetRepository.findByYear(Integer.valueOf(requestedYear));
            totalExpense = expenseRepository.findByYear(Integer.valueOf(requestedYear));
        } else  {
            totalBudget = budgetRepository.findByMonthAndYear(Integer.valueOf(requestedYear), Integer.valueOf(requestedMonth));
            totalExpense = expenseRepository.findByMonthAndYear(Integer.valueOf(requestedYear), Integer.valueOf(requestedMonth));
        }
        List<String> categoryList = commonUtils.fetchDistinctSubCategories();

        DecimalFormat df = new DecimalFormat("#.##");

        JSONArray jsonArray = new JSONArray();
        for (String cat : categoryList) {
            JSONObject json = new JSONObject();

            json.put("superCategory", cat);

            for (Budget budgetObj : totalBudget) {
                if (budgetObj.getSuperCategory().equalsIgnoreCase(cat)) {
                    if (json.has("budget")) {
                        double budObj = 0;
                        if (json.get("budget") instanceof Integer) {
                            budObj = (double) json.get("budget");
                        } else if (json.get("budget") instanceof Double) {
                            budObj = (double) json.get("budget");
                        } else if (json.get("budget") instanceof Long) {
                            budObj = (double) json.get("budget");
                        } else {
                            budObj = (double) json.get("budget");
                        }
                        budObj = budObj + (double) budgetObj.getPrice();
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
                if (expenseObj.getSuperCategory().equalsIgnoreCase(cat)) {
                    if (json.has("expense")) {
                        double expObj = 0;
                        if (json.get("expense") instanceof Integer) {
                            expObj = (double) json.get("expense");
                        } else if (json.get("expense") instanceof Double) {
                            expObj = (double) json.get("expense");
                        } else if (json.get("expense") instanceof Long) {
                            expObj = (double) json.get("expense");
                        } else {
                            expObj = (double) json.get("expense");
                        }
                        expObj = expObj + (double) expenseObj.getPrice();
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
                if (budgetObj.getSuperCategory().equalsIgnoreCase(cat)) {
                    if (json.has("budget")) {
                        double budObj = 0;
                        if (json.get("budget") instanceof Integer) {
                            budObj = (double) json.get("budget");
                        } else if (json.get("budget") instanceof Double) {
                            budObj = (double) json.get("budget");
                        } else if (json.get("budget") instanceof Long) {
                            budObj = (double) json.get("budget");
                        } else {
                            budObj = (double) json.get("budget");
                        }
                        budObj = budObj + (double) budgetObj.getPrice();
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
                if (expenseObj.getSuperCategory().equalsIgnoreCase(cat)) {
                    if (json.has("expense")) {
                        double expObj = 0;
                        if (json.get("expense") instanceof Integer) {
                            expObj = (double) json.get("expense");
                        } else if (json.get("expense") instanceof Double) {
                            expObj = (double) json.get("expense");
                        } else if (json.get("expense") instanceof Long) {
                            expObj = (double) json.get("expense");
                        } else {
                            expObj = (double) json.get("expense");
                        }
                        expObj = expObj + (double) expenseObj.getPrice();
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
        return reportsRepository.findSuperCategoryByMonthAndYear(year, month, superCategory);
    }

    public ArrayList calculateDataForTrendsReport() {
        List<Budget> budgets = budgetRepository.findAllByOrderByDateAsc();
        List<Expense> expenses = expenseRepository.findAllByOrderByDateAsc();
        Map<String, JSONObject> monthlyTotalsMap = new TreeMap<>(Collections.reverseOrder());
        DecimalFormat df = new DecimalFormat("#.##");

        for (Expense expense : expenses) {
            String monthYear = CommonUtils.getMonthYear(expense.getDate());
            if (!monthlyTotalsMap.containsKey(monthYear)) {
                JSONObject json = new JSONObject();
                json.put("month", monthYear).put("totalExpense", 0.0).put("totalBudget", 0.0);
                monthlyTotalsMap.put(monthYear, json);
            }
            JSONObject monthlyTotal = monthlyTotalsMap.get(monthYear);
            double expObj = (double) monthlyTotal.get("totalExpense");
            expObj = expObj + (double) expense.getPrice();
            String formattedValue = df.format(expObj);
            double result = Double.parseDouble(formattedValue);
            monthlyTotal.put("totalExpense", result);
        }

        for (Budget budget : budgets) {
            String monthYear = CommonUtils.getMonthYear(budget.getDate());
            if (!monthlyTotalsMap.containsKey(monthYear)) {
                JSONObject json = new JSONObject();
                json.put("month", monthYear).put("totalExpense", 0.0).put("totalBudget", 0.0);
                monthlyTotalsMap.put(monthYear, json);
            }
            JSONObject monthlyTotal = monthlyTotalsMap.get(monthYear);
            double expObj = (double) monthlyTotal.get("totalBudget");
            expObj = expObj + (double) budget.getPrice();
            String formattedValue = df.format(expObj);
            double result = Double.parseDouble(formattedValue);
            monthlyTotal.put("totalBudget", result);
        }
        return new ArrayList<>(monthlyTotalsMap.values());
    }

    public List<ParentCategoryDTO> calculateDataForCategoryReport(String requestedYear, String requestedMonth) {
        List<Category> categoryList = commonUtils.fetchAllCategories();
        Collections.sort(categoryList);

        List<Budget> budgetList = null;
        List<Expense> expenseList = null;
        if (requestedMonth.equalsIgnoreCase ("All")) {
            budgetList = budgetRepository.findByYear(Integer.valueOf(requestedYear));
            expenseList = expenseRepository.findByYear(Integer.valueOf(requestedYear));
        } else {
            budgetList = budgetRepository.findByMonthAndYear(Integer.valueOf(requestedYear), Integer.valueOf(requestedMonth));
            expenseList = expenseRepository.findByMonthAndYear(Integer.valueOf(requestedYear), Integer.valueOf(requestedMonth));
        }

        List<String> distinctParentCatList = categoryList.stream().map(Category::getParentCategory).distinct().collect(Collectors.toList());
        System.out.println(distinctParentCatList);
        List<ParentCategoryDTO> parentCategoryDTOList = new ArrayList<>();
        for (String distinctParentCatObj : distinctParentCatList) {
            ParentCategoryDTO parentCategoryDTO = new ParentCategoryDTO();
            parentCategoryDTO.setName(distinctParentCatObj);
            List<String> distinctSuperCatList = fetchDistinctSuperCategories(distinctParentCatObj, categoryList);
            List<SuperCategoryDTO> superCatDtoList = new ArrayList<>();
            double parentCatBudget = 0;
            double parentCatExpense = 0;
            for (String distinctSuperCatObj : distinctSuperCatList) {
                SuperCategoryDTO superCategoryDTO = new SuperCategoryDTO();
                superCategoryDTO.setName(distinctSuperCatObj);
                List<Category> filteredCategoryList = categoryList.stream().filter(item -> item.getSuperCategory().equalsIgnoreCase(distinctSuperCatObj)).collect(Collectors.toList());
                List<CategoryDTO> catDtoList = new ArrayList<>();
                double superCatBudget = 0;
                double superCatExpense = 0;
                for (Category distinctCatObj : filteredCategoryList) {
                    CategoryDTO categoryDTO = new CategoryDTO();
                    categoryDTO.setName(distinctCatObj.getCategory());
                    categoryDTO.setCompleted(calculateExpenseValue(distinctCatObj.getCategory(), expenseList).get("completed").toString());
                    categoryDTO.setExpense(Double.valueOf(calculateExpenseValue(distinctCatObj.getCategory(), expenseList).get("expense").toString()));
                    superCatExpense = superCatExpense + categoryDTO.getExpense();
                    categoryDTO.setBudget(calculateBudgetValue(distinctCatObj.getCategory(), budgetList));
                    superCatBudget = superCatBudget + categoryDTO.getBudget();
                    catDtoList.add(categoryDTO);
                }
                superCategoryDTO.setCategoryDtoList(catDtoList);
                superCategoryDTO.setBudget(superCatBudget);
                superCategoryDTO.setExpense(superCatExpense);
                parentCatBudget = parentCatBudget + superCategoryDTO.getBudget();
                parentCatExpense = parentCatExpense + superCategoryDTO.getExpense();
                List<String> completedList = catDtoList.stream().map(CategoryDTO::getCompleted).distinct().collect(Collectors.toList());
                if(!completedList.isEmpty() && completedList.size() == 1) {
                    superCategoryDTO.setCompleted(completedList.get(0));
                } else {
                    superCategoryDTO.setCompleted("Partial");
                }
                superCatDtoList.add(superCategoryDTO);
            }
            parentCategoryDTO.setBudget(parentCatBudget);
            parentCategoryDTO.setExpense(parentCatExpense);
            parentCategoryDTO.setSuperCategoryDtoList(superCatDtoList);
            List<String> completedList = superCatDtoList.stream().map(SuperCategoryDTO::getCompleted).distinct().collect(Collectors.toList());
            if(!completedList.isEmpty() && completedList.size() == 1) {
                parentCategoryDTO.setCompleted(completedList.get(0));
            } else {
                parentCategoryDTO.setCompleted("Partial");
            }
            parentCategoryDTOList.add(parentCategoryDTO);
        }

        return parentCategoryDTOList;
    }

    private List<String> fetchDistinctSuperCategories(String parentCategoryObj, List<Category> categoryList) {
        List<String> distinctSuperCat = categoryList.stream()
                .filter(row -> parentCategoryObj.equalsIgnoreCase(row.getParentCategory()))
                .map(Category::getSuperCategory)
                .distinct()
                .collect(Collectors.toList());
        return distinctSuperCat;
    }

    private JSONObject calculateExpenseValue(String category, List<Expense> expenseList) {
        JSONObject json = new JSONObject();
        List<Expense> expenseFilteredList = expenseList.stream().filter(item -> item.getCategory().equalsIgnoreCase(category)).collect(Collectors.toList());
        if(!expenseFilteredList.isEmpty()) {
            double sum = expenseFilteredList.stream()
                    .mapToDouble(Expense::getPrice)
                    .sum();
            json.put("expense", sum);
            String result = "Partial";
            List<String> completedList = expenseFilteredList.stream().map(Expense::getCompleted).distinct().collect(Collectors.toList());
            if (!completedList.isEmpty() && completedList.size() == 1) {
                json.put("completed", completedList.get(0).equalsIgnoreCase("No") ? "Partial": "Yes");
            } else {
                json.put("completed", "Partial");
            }
        } else {
            json.put("expense", 0);
            json.put("completed", "No");
        }
        return json;
    }

    private double calculateBudgetValue(String category, List<Budget> budgetList) {
        double sum = 0;
        List<Budget> expenseFilteredList = budgetList.stream().filter(item -> item.getCategory().equalsIgnoreCase(category)).collect(Collectors.toList());
        sum = expenseFilteredList.stream()
                .mapToDouble(Budget::getPrice)
                .sum();
        return sum;
    }
}
