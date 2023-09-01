package com.aakbmir.expensetracker.controller;

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

    @PostMapping("/save-mul-budget")
    public ResponseEntity saveMulBudget(@RequestBody List<Budget> budgetList) {
        for (Budget cat : budgetList) {
            budgetService.saveBudget(cat);
        }
        return new ResponseEntity("Success", HttpStatus.OK);
    }

    @GetMapping("/get-budget/{budgetName}")
    private ResponseEntity getBudget(@PathVariable("budgetName") String budgetName) {
        Budget cat = budgetService.findByBudget(budgetName);
        return new ResponseEntity(cat, HttpStatus.OK);
    }

    @GetMapping("/get-all-budgets")
    public ResponseEntity getAllBudget() {
        List<Budget> catList = budgetService.getAllBudgets();
        return new ResponseEntity(catList, HttpStatus.OK);
    }

    @DeleteMapping("/del-budget/{id}")
    private void deleteBudget(@PathVariable("id") Long id) {
        budgetService.deleteBudget(id);
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
