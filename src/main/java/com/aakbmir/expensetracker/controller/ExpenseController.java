package com.aakbmir.expensetracker.controller;

import com.aakbmir.expensetracker.entity.Expense;
import com.aakbmir.expensetracker.service.ExpenseService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/expense")
@CrossOrigin("*")
public class ExpenseController {

    @GetMapping("/get-status")
    public ResponseEntity getStatus() {
        System.out.println("Server is running");
        return new ResponseEntity("server is running", HttpStatus.OK);
    }

    @Autowired
    ExpenseService expenseService;

    @PostMapping("/save-expense")
    public ResponseEntity saveExpense(@RequestBody Expense expense) {
        if (expense != null && expense.getDate() != null
                && expense.getNote() != null && expense.getPrice() != 0) {
            Expense cat = expenseService.saveExpense(expense);
            return new ResponseEntity(cat, HttpStatus.OK);
        } else {
            JSONObject json = new JSONObject().put("message", "invalid Request");
            return new ResponseEntity(json.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-expense")
    private ResponseEntity getExpense(@RequestParam(name = "expenseName") String expenseName) {
        List<Expense> expense = expenseService.findByCategory(expenseName);
        return new ResponseEntity(expense, HttpStatus.OK);
    }

    @GetMapping("/get-current-expense")
    public ResponseEntity getCurrentExpense(@RequestParam(name = "month") String month, @RequestParam(name = "year") String year) {
        List<Expense> expensesForMonth = expenseService.findByMonthAndYear(Integer.valueOf(year), Integer.valueOf(month));
        return new ResponseEntity(expensesForMonth, HttpStatus.OK);
    }

    @DeleteMapping("/del-expense/{id}")
    private void deleteExpense(@PathVariable("id") Long id) {
        expenseService.deleteExpense(id);
    }

    @PostMapping("/update-expense")
    public ResponseEntity updateExpense(@RequestBody Expense expense) {
        Optional<Expense> expenseData = expenseService.findById(expense.getId());
        if (expenseData.isPresent()) {
            Expense expenseObj = expenseData.get();
            expenseObj.setId(expense.getId());
            expenseObj.setCategory(expense.getCategory());
            expenseObj.setPrice(expense.getPrice());
            expenseObj.setCompleted(expense.getCompleted());
            expenseObj.setDate(expense.getDate());
            expenseObj.setNote(expense.getNote());
            Expense updateCat = expenseService.saveExpense(expenseObj);
            return new ResponseEntity(updateCat, HttpStatus.OK);
        }
        return new ResponseEntity(expense, HttpStatus.OK);
    }

}
