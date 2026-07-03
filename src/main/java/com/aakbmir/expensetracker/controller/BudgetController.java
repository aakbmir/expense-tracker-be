package com.aakbmir.expensetracker.controller;

import com.aakbmir.expensetracker.entity.Budget;
import com.aakbmir.expensetracker.entity.Category;
import com.aakbmir.expensetracker.service.BudgetService;
import com.aakbmir.expensetracker.utils.CommonUtils;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
    public ResponseEntity<List<Budget>> addAllBudgets(@RequestParam String month, @RequestParam String year) {
        List<Budget> isAdded = budgetService.addAllBudgets(Integer.parseInt(year), Integer.parseInt(month));
        return new ResponseEntity<>(isAdded, HttpStatus.OK);
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
