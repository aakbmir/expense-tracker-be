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

    @PostMapping("/save-mul-expense")
    public ResponseEntity saveMulExpense(@RequestBody List<Expense> expenseList) {
        for (Expense cat : expenseList) {
            expenseService.saveExpense(cat);
        }
        return new ResponseEntity("Success", HttpStatus.OK);
    }

    @GetMapping("/get-expense/{expenseName}")
    private ResponseEntity getExpense(@PathVariable("expenseName") String expenseName) {
        List<Expense> cat = expenseService.findByCategory(expenseName);
        return new ResponseEntity(cat, HttpStatus.OK);
    }

    @GetMapping("/get-current-expense")
    public ResponseEntity getCurrentExpense(@RequestParam(name = "month") String month, @RequestParam(name = "year") String year) {
        List<Expense> expensesForMonth = expenseService.findByMonthAndYear(Integer.valueOf(year), Integer.valueOf(month));
        return new ResponseEntity(expensesForMonth, HttpStatus.OK);

//        String response = "[{\"id\":2,\"category\":\"Parking\",\"price\":100,\"date\":\"2023-09-01T20:00:00.000+00:00\",\"note\":\"Top Up\"},{\"id\":2,\"category\":\"Washing\",\"price\":34,\"date\":\"2023-09-01T20:00:00.000+00:00\",\"note\":\"Al Helal\"},{\"id\":2,\"category\":\"Grocery\",\"price\":124,\"date\":\"2023-09-02T20:00:00.000+00:00\",\"note\":\"Lulu\"},{\"id\":2,\"category\":\"Home\",\"price\":455,\"date\":\"2023-09-03T20:00:00.000+00:00\",\"note\":\"Abu Transfer\"},{\"id\":2,\"category\":\"LIC\",\"price\":100,\"date\":\"2023-09-03T20:00:00.000+00:00\",\"note\":\"Premium\"}]";
//        return new ResponseEntity(response.toString(), HttpStatus.OK);
    }

    @GetMapping("/get-all-expenses")
    public ResponseEntity getAllExpense() {
        List<Expense> catList = expenseService.getAllExpenses();
        return new ResponseEntity(catList, HttpStatus.OK);
    }

    @DeleteMapping("/del-expense/{id}")
    private void deleteExpense(@PathVariable("id") Long id) {
        expenseService.deleteExpense(id);
    }

    @PostMapping("/update-expense")
    public ResponseEntity updateExpense(@RequestBody Expense expense) {
        Optional<Expense> expenseObj = expenseService.findById(expense.getId());

        if (expenseObj.isPresent()) {
            Expense cat = expenseObj.get();
            cat.setId(expense.getId());
            cat.setPrice(expense.getPrice());
            cat.setDate(expense.getDate());
            cat.setNote(expense.getNote());
            Expense updateCat = expenseService.updateExpense(cat);
            return new ResponseEntity(updateCat, HttpStatus.OK);
        }
        return new ResponseEntity(expense, HttpStatus.OK);
    }

}
