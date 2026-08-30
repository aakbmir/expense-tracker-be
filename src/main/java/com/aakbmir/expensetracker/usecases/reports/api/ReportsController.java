package com.aakbmir.expensetracker.usecases.reports.api;

import com.aakbmir.expensetracker.usecases.category.bff.dto.CategoryApiDTO;
import com.aakbmir.expensetracker.usecases.category.bff.dto.ParentCategoryDTO;
import com.aakbmir.expensetracker.usecases.expense.api.dto.ExpenseDTO;
import com.aakbmir.expensetracker.usecases.income.repository.entity.Income;
import com.aakbmir.expensetracker.usecases.income.service.IncomeService;
import com.aakbmir.expensetracker.usecases.reports.api.dto.GroupReport;
import com.aakbmir.expensetracker.usecases.reports.service.ReportsService;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/reports")
@CrossOrigin("*")
@RequiredArgsConstructor
public class ReportsController {

    private final ReportsService reportsService;

    private final IncomeService incomeService;

    @GetMapping("/get-distinct-categories")
    public ResponseEntity<?> getDistinctCategories() {
        List<CategoryApiDTO> catList = reportsService.getDistinctCategories();
        return new ResponseEntity<>(catList, HttpStatus.OK);
    }

    @GetMapping("/overview-report")
    public ResponseEntity<?> getMonthlyOverview(@RequestParam(name = "month") String month,
                                                @RequestParam(name = "year") String year) {

        JSONArray expensesForMonth = reportsService.calculateDataForOverviewReport(year, month);
        List<Income> incomeList = incomeService.findByMonthAndYear(Integer.parseInt(year), Integer.parseInt(month));
        GroupReport groupReport = new GroupReport();
        groupReport.setExpenses(expensesForMonth.toList());
        groupReport.setIncome(!incomeList.isEmpty() ? incomeList.get(0): null);
        return new ResponseEntity<>(groupReport, HttpStatus.OK);
    }

/*    @GetMapping("/super-category-report")
    public ResponseEntity<?> superCategoryReport(@RequestParam(name = "month") String month, @RequestParam(name = "year") String year) {
        JSONArray expensesForMonth = reportsService.calculateDataForSuperCategoryReport(Integer.parseInt(year), Integer.parseInt(month));
        return new ResponseEntity<>(expensesForMonth.toString(), HttpStatus.OK);
    }

    @GetMapping("/super-category-report-details")
    public ResponseEntity<?> superCategoryReportDetails(@RequestParam(name = "superCategory") String superCategory, @RequestParam(name = "month") String month, @RequestParam(name = "year") String year) {
        List<Expense> json = reportsService.fetchSuperCategoryReportDetails(superCategory, Integer.valueOf(year), Integer.valueOf(month));
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    @GetMapping("/category-report-details")
    public ResponseEntity<?> categoryReportDetails(@RequestParam(name = "category") String category, @RequestParam(name = "month") String month, @RequestParam(name = "year") String year) {
        List<Expense> json = reportsService.fetchCategoryReportDetails(category, Integer.valueOf(year), Integer.valueOf(month));
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    @GetMapping("/refresh-cache")
    public ResponseEntity<?> refreshCache() {
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @GetMapping("/savings-report")
    public ResponseEntity<?> getSavingsReport() {
        ArrayList<?> savingsReportDTO = reportsService.calculateDataForSavingsReport();
        return new ResponseEntity<>(savingsReportDTO.toString(), HttpStatus.OK);
    }

    @GetMapping("/bank-report")
    public ResponseEntity<?> getCategoryReport() {
        ArrayList<?> list = reportsService.calculateDataForBankReport();
        return new ResponseEntity<>(list.toString(), HttpStatus.OK);
    }
*/

    @GetMapping("/get-expense")
    private ResponseEntity<?> getExpense(@RequestParam(name = "expenseName") String expenseName,
                                         @RequestParam String option) {
        List<ExpenseDTO> cat = reportsService.findByCategory(expenseName, option);
        return new ResponseEntity<>(cat, HttpStatus.OK);
    }

    @GetMapping("/trends-report")
    public ResponseEntity<?> getTrendsOverview() {
        ArrayList<?> expensesForMonth = reportsService.calculateDataForTrendsReport();
        return new ResponseEntity<>(expensesForMonth.toString(), HttpStatus.OK);
    }

    @GetMapping("/grouped-report")
    public ResponseEntity<?> getCategoryReport(@RequestParam(name = "month") String month, @RequestParam(name = "year") String year) {
        List<ParentCategoryDTO> list = reportsService.calculateDataForCategoryReport(year, month);
        List<Income> incomeList = incomeService.findByMonthAndYear(Integer.parseInt(year), Integer.parseInt(month));
        GroupReport groupReport = new GroupReport();
        groupReport.setParentCategoryDTOList(list);
        groupReport.setIncome(incomeList.get(0));
        return new ResponseEntity<>(groupReport, HttpStatus.OK);
    }

}
