package com.aakbmir.expensetracker.controller;

import com.aakbmir.expensetracker.entity.Expense;
import com.aakbmir.expensetracker.entity.Income;
import com.aakbmir.expensetracker.service.IncomeService;
import com.aakbmir.expensetracker.service.ReportsService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/reports")
@CrossOrigin("*")
public class ReportsController {

    @Autowired
    ReportsService reportsService;

    @GetMapping("/total-overview")
    public ResponseEntity getMonthlyOverview(@RequestParam(name = "month") String month, @RequestParam(name = "year") String year) {
        JSONObject expensesForMonth = reportsService.calculateMonthlyView(Integer.valueOf(year), Integer.valueOf(month));
        return new ResponseEntity(expensesForMonth.toString(), HttpStatus.OK);
    }
}
