package com.aakbmir.expensetracker.controller;

import com.aakbmir.expensetracker.entity.Expense;
import com.aakbmir.expensetracker.entity.Income;
import com.aakbmir.expensetracker.service.IncomeService;
import com.aakbmir.expensetracker.service.ReportsService;
import org.json.JSONArray;
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

    @GetMapping("/overview-category")
    public ResponseEntity getMonthlyOverview(@RequestParam(name = "month") String month, @RequestParam(name = "year") String year) {
        JSONObject expensesForMonth = reportsService.calculateMonthlyOverview(Integer.valueOf(year), Integer.valueOf(month));
        return new ResponseEntity(expensesForMonth.toString(), HttpStatus.OK);
    }

    @GetMapping("/monthly-category")
    public ResponseEntity getMonthlyCategory(@RequestParam(name = "month") String month, @RequestParam(name = "year") String year) {
        JSONArray expensesForMonth = reportsService.calculateMonthlyCategoryView(null,Integer.valueOf(year), Integer.valueOf(month));
        return new ResponseEntity(expensesForMonth.toString(), HttpStatus.OK);
    }

    @GetMapping("/monthly-parent")
    public ResponseEntity getMonthlyParent(@RequestParam(name = "month") String month, @RequestParam(name = "year") String year) {
        JSONArray expensesForMonth = reportsService.calculateMonthlyParentView(Integer.valueOf(year), Integer.valueOf(month));
        return new ResponseEntity(expensesForMonth.toString(), HttpStatus.OK);
    }

    @GetMapping("/fetch-Parent-Category-Details")
    public ResponseEntity fetchParentCategoryDetails(@RequestParam(name = "parent") String parent, @RequestParam(name = "month") String month, @RequestParam(name = "year") String year) {
        JSONArray expensesForMonth = reportsService.calculateMonthlyCategoryView(parent,Integer.valueOf(year), Integer.valueOf(month));
        return new ResponseEntity(expensesForMonth.toString(), HttpStatus.OK);
    }

    @GetMapping("/trends-overview")
    public ResponseEntity getTrendsOverview(@RequestParam(name = "month") String month, @RequestParam(name = "year") String year) {
        JSONObject expensesForMonth = reportsService.calculateTrendsOverview(Integer.valueOf(year), Integer.valueOf(month));
        return new ResponseEntity(expensesForMonth.toString(), HttpStatus.OK);
    }
}
