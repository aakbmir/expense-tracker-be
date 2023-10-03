package com.aakbmir.expensetracker.controller;

import com.aakbmir.expensetracker.DTO.ParentCategoryDTO;
import com.aakbmir.expensetracker.DTO.ReportDTO;
import com.aakbmir.expensetracker.entity.Expense;
import com.aakbmir.expensetracker.entity.MonthlyTotal;
import com.aakbmir.expensetracker.service.ReportsService;
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

/*    @GetMapping("/overview-category")
    public ResponseEntity getMonthlyOverview(@RequestParam(name = "month") String month, @RequestParam(name = "year") String year) {
        JSONObject expensesForMonth = reportsService.calculateMonthlyOverview(Integer.valueOf(year), Integer.valueOf(month));
        return new ResponseEntity(expensesForMonth.toString(), HttpStatus.OK);
    }

    @GetMapping("/monthly-category")
    public ResponseEntity getMonthlyCategory(@RequestParam(name = "month") String month, @RequestParam(name = "year") String year) {
        JSONArray expensesForMonth = reportsService.calculateMonthlySuperCategoryView(null,Integer.valueOf(year), Integer.valueOf(month));
        return new ResponseEntity(expensesForMonth.toString(), HttpStatus.OK);
    }

    @GetMapping("/monthly-parent")
    public ResponseEntity getMonthlyParent(@RequestParam(name = "month") String month, @RequestParam(name = "year") String year) {
        JSONArray expensesForMonth = reportsService.calculateMonthlyParentView(Integer.valueOf(year), Integer.valueOf(month));
        return new ResponseEntity(expensesForMonth.toString(), HttpStatus.OK);
    }

    @GetMapping("/fetch-Category-Details")
    public ResponseEntity fetchCategoryDetails(@RequestParam(name = "category") String category, @RequestParam(name = "month") String month, @RequestParam(name = "year") String year) {
        JSONArray expensesForMonth = reportsService.calculateMonthlyDetailsView(category, Integer.valueOf(year), Integer.valueOf(month));
        return new ResponseEntity(expensesForMonth.toString(), HttpStatus.OK);
    }

    @GetMapping("/get-distinct-categories")
    public ResponseEntity getDistinctCategories() {
        List<String> catList = reportsService.getDistinctCategories();
        return new ResponseEntity(catList, HttpStatus.OK);
    }*/

    @GetMapping("/overview-report")
    public ResponseEntity getMonthlyOverview(@RequestParam(name = "month") String month, @RequestParam(name = "year") String year) {
        JSONArray expensesForMonth = reportsService.calculateDataForOverviewReport(Integer.valueOf(year), Integer.valueOf(month));
        return new ResponseEntity(expensesForMonth.toString(), HttpStatus.OK);
    }

    @GetMapping("/category-report")
    public ResponseEntity getCategoryReport(@RequestParam(name = "month") String month, @RequestParam(name = "year") String year) {
        JSONArray expensesForMonth = reportsService.calculateDataForCategoryReport(Integer.valueOf(year), Integer.valueOf(month));
        return new ResponseEntity(expensesForMonth.toString(), HttpStatus.OK);
    }

    @GetMapping("/category-report-details")
    public ResponseEntity categoryReportDetails(@RequestParam(name = "category") String category, @RequestParam(name = "month") String month, @RequestParam(name = "year") String year) {
        List<Expense> json = reportsService.fetchCategoryReportDetails(category, Integer.valueOf(year), Integer.valueOf(month));
        return new ResponseEntity(json, HttpStatus.OK);
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

    @GetMapping("/general")
    public ResponseEntity general(@RequestParam(name = "month") String month, @RequestParam(name = "year") String year) {
        List<ParentCategoryDTO>  list = reportsService.general(Integer.valueOf(year), Integer.valueOf(month));
        return new ResponseEntity(list, HttpStatus.OK);
    }
}
