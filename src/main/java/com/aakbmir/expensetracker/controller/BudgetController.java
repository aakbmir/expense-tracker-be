package com.aakbmir.expensetracker.controller;

import com.aakbmir.expensetracker.DTO.BudgetDTO;
import com.aakbmir.expensetracker.entity.Budget;
import com.aakbmir.expensetracker.service.BudgetService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/budget")
@CrossOrigin("*")
public class BudgetController {

    @Autowired
    BudgetService budgetService;

    @GetMapping("/add-all-budgets")
    public ResponseEntity addAllBudgets() {
        List<Budget> isAdded = budgetService.addAllBudgets();
        return new ResponseEntity(isAdded, HttpStatus.OK);
    }

    @PostMapping("/save-budget")
    public ResponseEntity saveBudget(@RequestBody Budget budget) {
        if (budget != null && budget.getCategory() != null && budget.getDate() != null) {
            Budget cat = budgetService.saveBudget(budget);
            return new ResponseEntity(cat, HttpStatus.OK);
        } else {
            JSONObject json = new JSONObject().put("message", "invalid Request");
            return new ResponseEntity(json.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-current-budget")
    public ResponseEntity getCurrentBudget(@RequestParam(name = "month") String month, @RequestParam(name = "year") String year) {
        List<BudgetDTO> budgetsForMonth = budgetService.findByMonthAndYear(Integer.valueOf(year), Integer.valueOf(month));
        return new ResponseEntity(budgetsForMonth, HttpStatus.OK);
    }

    @PostMapping("/update-budget")
    public ResponseEntity updateBudget(@RequestBody Budget budget) {
        Optional<Budget> budgetObj = budgetService.findById(budget.getId());
        if (budgetObj.isPresent()) {
            Budget cat = budgetObj.get();
            cat.setCategory(budget.getCategory());
            cat.setDate(budget.getDate());
            cat.setPrice(budget.getPrice());
            Budget updateCat = budgetService.updateBudget(cat);
            return new ResponseEntity(updateCat, HttpStatus.OK);
        }
        return new ResponseEntity(budget, HttpStatus.OK);
    }
}
