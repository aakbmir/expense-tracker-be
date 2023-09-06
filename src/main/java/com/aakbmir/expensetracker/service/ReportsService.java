package com.aakbmir.expensetracker.service;

import com.aakbmir.expensetracker.entity.Budget;
import com.aakbmir.expensetracker.entity.Category;
import com.aakbmir.expensetracker.entity.Expense;
import com.aakbmir.expensetracker.repository.BudgetRepository;
import com.aakbmir.expensetracker.repository.CategoryRepository;
import com.aakbmir.expensetracker.repository.ExpenseRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.*;

@Service
public class ReportsService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ExpenseRepository expenseRepository;

    @Autowired
    BudgetRepository budgetRepository;

    public JSONObject calculateMonthlyOverview(int year, int month) {
        List<Double> totalBudget = budgetRepository.getSumByMonthAndYear(year, month);
        double budget = 0.0;
        double expense = 0.0;
        double deviation = 0.0;
        if (!totalBudget.isEmpty()) {
            for (Double budgetObj : totalBudget) {
                budget += budgetObj;
            }
        }
        List<Double> totalExpense = expenseRepository.getSumByMonthAndYear(year, month);
        if (!totalExpense.isEmpty()) {
            for (Double expenseObj : totalExpense) {
                expense += expenseObj;
            }
        }
        BigDecimal b1 = new BigDecimal(Double.toString(budget));
        BigDecimal b2 = new BigDecimal(Double.toString(expense));
        deviation = b1.subtract(b2).doubleValue();
        JSONObject json = new JSONObject();
        json.put("totalBudget", budget);
        json.put("totalExpense", expense);
        json.put("totalDeviation", deviation);
        return json;
    }

    public JSONArray calculateMonthlyCategoryView(String parent, int year, int month) {
        List<Category> categories = new ArrayList<>();
        if(parent !=null) {
            categories = categoryRepository.fetchParentCategory(parent);
        } else {
            categories = categoryRepository.findAllByOrderByParentAscCategoryAsc();
        }
        List<Budget> totalBudget = budgetRepository.findByMonthAndYear(year, month);
        List<Expense> totalExpense = expenseRepository.findByMonthAndYear(year, month);

        DecimalFormat df = new DecimalFormat("#.##");

        JSONArray jsonArray = new JSONArray();
        for (Category cat : categories) {
            JSONObject json = new JSONObject();
            json.put("parent", cat.getParent());
            json.put("category", cat.getCategory());
            json.put("budget", 0);
            json.put("expense", 0.0);
            for (Budget budgetObj : totalBudget) {
                if (budgetObj.getCategory().equalsIgnoreCase(cat.getCategory())) {
                    json.put("budget", budgetObj.getPrice());
                }
            }
            for (Expense expenseObj : totalExpense) {
                if (expenseObj.getCategory().equalsIgnoreCase(cat.getCategory())) {
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
            String formattedValue = df.format((double) json.get("budget") - (double) json.get("expense"));
            double result = Double.parseDouble(formattedValue);
            json.put("deviate", result);
            jsonArray.put(json);
        }
        return jsonArray;
    }

    public JSONArray calculateMonthlyParentView(int year, int month) {
        List<String> parents = categoryRepository.fetchParentCategory();
        List<Budget> totalBudget = budgetRepository.findByMonthAndYear(year, month);
        List<Expense> totalExpense = expenseRepository.findByMonthAndYear(year, month);

        DecimalFormat df = new DecimalFormat("#.##");

        JSONArray jsonArray = new JSONArray();
        for (String parent : parents) {
            JSONObject json = new JSONObject();
            json.put("parent", parent);
            json.put("budget", 0.0);
            json.put("expense", 0.0);
            for (Budget budgetObj : totalBudget) {
                if (budgetObj.getParent().equalsIgnoreCase(parent)) {
                    if (json.has("budget")) {
                        double expObj = 0;
                        if (json.get("budget") instanceof Integer) {
                            expObj = (double) json.get("budget");
                        } else if (json.get("budget") instanceof Double) {
                            expObj = (double) json.get("budget");
                        } else if (json.get("budget") instanceof Long) {
                            expObj = (double) json.get("budget");
                        } else {
                            expObj = (double) json.get("budget");
                        }
                        expObj = expObj + (double) budgetObj.getPrice();
                        String formattedValue = df.format(expObj);
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
                if (expenseObj.getParent().equalsIgnoreCase(parent)) {
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
            String formattedValue = df.format((double) json.get("budget") - (double) json.get("expense"));
            double result = Double.parseDouble(formattedValue);
            json.put("deviate", result);
            jsonArray.put(json);
        }
        return jsonArray;
    }

    private String getParent(String expenseCategory, List<Category> categories) {
        String parent = "";
        for (Category category : categories) {
            if (expenseCategory.equalsIgnoreCase(category.getCategory())) {
                parent = category.getParent();
                break;
            }
        }
        return parent;
    }

    public JSONObject calculateTrendsOverview(int year, int month) {
        List<Budget> totalBudget = budgetRepository.findAll();
        List<Expense> totalExpense = expenseRepository.findAll();

        Map<YearMonth, Double> monthlyExpenses = new HashMap<>();

        for (Expense expense : totalExpense) {

            LocalDate localDate = expense.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();


            YearMonth yearMonth = YearMonth.from(localDate);
            double price = expense.getPrice();

            monthlyExpenses.put(yearMonth, monthlyExpenses.getOrDefault(yearMonth, 0.0) + price);
        }

        double budget = 0.0;
        double expense = 0.0;
        double deviation = 0.0;


/*
        if (!totalBudget.isEmpty()) {
            for (Double budgetObj : totalBudget) {
                budget += budgetObj;
            }
        }
        List<Double> totalExpense = expenseRepository.getSumByMonthAndYear(year, month);
        if (!totalExpense.isEmpty()) {
            for (Double expenseObj : totalExpense) {
                expense += expenseObj;
            }
        }*/
        BigDecimal b1 = new BigDecimal(Double.toString(budget));
        BigDecimal b2 = new BigDecimal(Double.toString(expense));
        deviation = b1.subtract(b2).doubleValue();
        JSONObject json = new JSONObject();
        json.put("totalBudget", budget);
        json.put("totalExpense", expense);
        json.put("totalDeviation", deviation);
        return json;
    }

}
