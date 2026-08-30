package com.aakbmir.expensetracker.usecases.expense.api;

import com.aakbmir.expensetracker.usecases.expense.api.dto.ExpenseDTO;
import com.aakbmir.expensetracker.usecases.expense.service.ExpenseService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/expense")
@CrossOrigin("*")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping("/save-expense")
    public ResponseEntity<?> saveExpense(@NotNull @Valid @RequestBody ExpenseDTO expenseDTO) {
        expenseDTO = expenseService.saveExpense(expenseDTO);
        return new ResponseEntity<>(expenseDTO, HttpStatus.OK);
    }

//    @GetMapping("/get-expense")
//    public ResponseEntity<?> getExpense(@RequestParam(name = "expenseName") String expenseName) {
//        List<Expense> expense = expenseService.findByCategory(expenseName);
//        return new ResponseEntity<>(expense, HttpStatus.OK);
//    }

    @GetMapping("/get-current-expense")
    public ResponseEntity<?> getCurrentExpense(@RequestParam(name = "month") String month,
                                               @RequestParam(name = "year") String year) {

        List<ExpenseDTO> expensesForMonth = expenseService.findByMonthAndYear(Integer.parseInt(year),
                Integer.parseInt(month));
        return new ResponseEntity<>(expensesForMonth, HttpStatus.OK);
    }

    @DeleteMapping("/del-expense/{id}")
    public void deleteExpense(@PathVariable("id") Long id) {
        expenseService.deleteExpense(id);
    }

    @PostMapping("/update-expense")
    public ResponseEntity<?> updateExpense(@RequestBody ExpenseDTO expenseDTO) {
        expenseService.updateExpense(expenseDTO);
        return new ResponseEntity<>(expenseDTO, HttpStatus.OK);
    }
}
