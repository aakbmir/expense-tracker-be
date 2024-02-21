package com.aakbmir.expensetracker.controller;

import com.aakbmir.expensetracker.entity.Budget;
import com.aakbmir.expensetracker.entity.Category;
import com.aakbmir.expensetracker.service.BudgetService;
import com.aakbmir.expensetracker.utils.CommonUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/budget")
@CrossOrigin("*")
public class BudgetController {

    @Autowired
    BudgetService budgetService;

    @Autowired
    CommonUtils commonUtils;

    @GetMapping("/add-all-budgets")
    public ResponseEntity<List<Budget>> addAllBudgets() {
        List<Budget> isAdded = budgetService.addAllBudgets();
        return new ResponseEntity<>(isAdded, HttpStatus.OK);
    }

    @PostMapping("/save-budget")
    public ResponseEntity<Object> saveBudget(@RequestBody Budget budget) {
        if (budget != null && budget.getCategory() != null && budget.getDate() != null) {
            Category category = null;
            for (Category cat : commonUtils.fetchAllCategories()) {
                if (budget.getCategory().equalsIgnoreCase(cat.getCategory())) {
                    category = cat;
                    break;
                }
            }
            budget.setParentCategory(category.getParentCategory());
            budget.setSuperCategory(category.getSuperCategory());
            Budget cat = budgetService.saveBudget(budget);
            return new ResponseEntity<>(cat, HttpStatus.OK);
        } else {
            JSONObject json = new JSONObject().put("message", "invalid Request");
            return new ResponseEntity<>(json.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-current-budget")
    public ResponseEntity<List<Budget>> getCurrentBudget(@RequestParam(name = "month") String month, @RequestParam(name = "year") String year) {
        List<Budget> budgetsForMonth = budgetService.findByMonthAndYear(Integer.parseInt(year), Integer.parseInt(month));
        return new ResponseEntity<>(budgetsForMonth, HttpStatus.OK);
    }

    @PostMapping("/update-budget")
    public ResponseEntity<Budget> updateBudget(@RequestBody Budget budget) {
        Budget updateCat = budgetService.updateBudget(budget);
        return new ResponseEntity<>(updateCat, HttpStatus.OK);
    }
}
