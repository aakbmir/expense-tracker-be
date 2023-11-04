package com.aakbmir.expensetracker.controller;

import com.aakbmir.expensetracker.DTO.ParentCategoryDTO;
import com.aakbmir.expensetracker.entity.Bank;
import com.aakbmir.expensetracker.entity.Category;
import com.aakbmir.expensetracker.entity.Expense;
import com.aakbmir.expensetracker.entity.MonthlyTotal;
import com.aakbmir.expensetracker.service.ReportsService;
import com.aakbmir.expensetracker.utils.CommonUtils;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/reports")
@CrossOrigin("*")
public class ReportsController {

    @Autowired
    ReportsService reportsService;

    @GetMapping("/overview-report")
    public ResponseEntity getMonthlyOverview(@RequestParam(name = "month") String month, @RequestParam(name = "year") String year) {
        JSONArray expensesForMonth = reportsService.calculateDataForOverviewReport(year, month);
        return new ResponseEntity(expensesForMonth.toString(), HttpStatus.OK);
    }

    @GetMapping("/super-category-report")
    public ResponseEntity superCategoryReport(@RequestParam(name = "month") String month, @RequestParam(name = "year") String year) {
        JSONArray expensesForMonth = reportsService.calculateDataForSuperCategoryReport(Integer.valueOf(year), Integer.valueOf(month));
        return new ResponseEntity(expensesForMonth.toString(), HttpStatus.OK);
    }

    @GetMapping("/super-category-report-details")
    public ResponseEntity superCategoryReportDetails(@RequestParam(name = "superCategory") String superCategory, @RequestParam(name = "month") String month, @RequestParam(name = "year") String year) {
        List<Expense> json = reportsService.fetchSuperCategoryReportDetails(superCategory, Integer.valueOf(year), Integer.valueOf(month));
        return new ResponseEntity(json, HttpStatus.OK);
    }

    @GetMapping("/trends-report")
    public ResponseEntity getTrendsOverview() {
        ArrayList<MonthlyTotal> expensesForMonth = reportsService.calculateDataForTrendsReport();
        return new ResponseEntity(expensesForMonth.toString(), HttpStatus.OK);
    }

    @GetMapping("/grouped-report")
    public ResponseEntity getCategoryReport(@RequestParam(name = "month") String month, @RequestParam(name = "year") String year) {
        List<ParentCategoryDTO> list = reportsService.calculateDataForCategoryReport(year, month);
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @GetMapping("/category-report-details")
    public ResponseEntity categoryReportDetails(@RequestParam(name = "category") String category, @RequestParam(name = "month") String month, @RequestParam(name = "year") String year) {
        List<Expense> json = reportsService.fetchCategoryReportDetails(category, Integer.valueOf(year), Integer.valueOf(month));
        return new ResponseEntity(json, HttpStatus.OK);
    }

    @GetMapping("/refresh-cache")
    public ResponseEntity refreshCache() {
        CommonUtils.categoryListCache.clear();
        return new ResponseEntity("success", HttpStatus.OK);
    }

    @GetMapping("/savings-report")
    public ResponseEntity getSavingsReport() {
        ArrayList<MonthlyTotal> savingsReportDTO = reportsService.calculateDataForSavingsReport();
        return new ResponseEntity(savingsReportDTO.toString(), HttpStatus.OK);
    }

    @GetMapping("/bank-report")
    public ResponseEntity getCategoryReport() {
        ArrayList list = reportsService.calculateDataForBankReport();
        return new ResponseEntity(list.toString(), HttpStatus.OK);
    }

    @GetMapping("/get-distinct-categories")
    public ResponseEntity getDistinctCategories() {
        List<Category> catList = reportsService.getDistinctCategories();
        return new ResponseEntity(catList, HttpStatus.OK);
    }

    @GetMapping("/get-expense")
    private ResponseEntity getExpense(@RequestParam(name = "expenseName") String expenseName, @RequestParam String option) {
        List<Expense> cat = reportsService.findByCategory(expenseName, option);
        return new ResponseEntity(cat, HttpStatus.OK);
    }

}
