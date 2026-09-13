package com.aakbmir.expensetracker.usecases.reports.api;

import com.aakbmir.expensetracker.usecases.category.bff.dto.CategoryApiDTO;
import com.aakbmir.expensetracker.usecases.category.bff.dto.ParentCategoryDTO;
import com.aakbmir.expensetracker.usecases.expense.api.dto.ExpenseDTO;
import com.aakbmir.expensetracker.usecases.income.repository.entity.Income;
import com.aakbmir.expensetracker.usecases.income.service.IncomeService;
import com.aakbmir.expensetracker.usecases.reports.api.dto.GroupReport;
import com.aakbmir.expensetracker.usecases.reports.api.dto.MainCategoryReportResponse;
import com.aakbmir.expensetracker.usecases.reports.api.dto.OverviewRecord;
import com.aakbmir.expensetracker.usecases.reports.service.ReportsService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
  public ResponseEntity<OverviewRecord> getMonthlyOverview(
      @RequestParam(name = "month") String month, @RequestParam(name = "year") String year) {
    OverviewRecord overviewRecord = reportsService.calculateAllReportFields(year, month);
    return new ResponseEntity<>(overviewRecord, HttpStatus.OK);
  }

  @GetMapping("/get-expense")
  private ResponseEntity<?> getExpense(
      @RequestParam(name = "expenseName") String expenseName, @RequestParam String option) {
    List<ExpenseDTO> cat = reportsService.findByCategory(expenseName, option);
    return new ResponseEntity<>(cat, HttpStatus.OK);
  }

  @GetMapping("/get-expense-categories")
  private ResponseEntity<?> getExpenseCategories(
      @RequestParam(name = "month") String month, @RequestParam(name = "year") String year) {
    List<MainCategoryReportResponse> responseList =
        reportsService.getExpenseCategories(year, month);
    return new ResponseEntity<>(responseList, HttpStatus.OK);
  }

  @GetMapping("/grouped-report")
  public ResponseEntity<?> getCategoryReport(
      @RequestParam(name = "month") String month, @RequestParam(name = "year") String year) {
    List<ParentCategoryDTO> list = reportsService.calculateDataForCategoryReport(year, month);
    List<Income> incomeList =
        incomeService.findByMonthAndYear(Integer.parseInt(year), Integer.parseInt(month));
    GroupReport groupReport = new GroupReport();
    groupReport.setParentCategoryDTOList(list);
    groupReport.setIncome(incomeList.get(0));
    return new ResponseEntity<>(groupReport, HttpStatus.OK);
  }
}
