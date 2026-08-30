package com.aakbmir.expensetracker.usecases.savings.api;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/savings")
@CrossOrigin("*")
public class SavingsController {

/*    @Autowired
    ExpenseService expenseService;

    @PostMapping("/save-expense")
    public ResponseEntity<?> saveExpense(@NotNull @Valid @RequestBody ExpenseDTO expenseDTO) {

        expenseDTO = expenseService.saveExpense(expenseDTO);
        return new ResponseEntity<>(expenseDTO, HttpStatus.OK);

    }

    @GetMapping("/get-expense")
    public ResponseEntity<?> getExpense(@RequestParam(name = "expenseName") String expenseName) {
        List<Expense> expense = expenseService.findByCategory(expenseName);
        return new ResponseEntity<>(expense, HttpStatus.OK);
    }

    @GetMapping("/get-current-expense")
    public ResponseEntity<?> getCurrentExpense(@RequestParam(name = "month") String month, @RequestParam(name = "year") String year) {
        List<Expense> expensesForMonth = expenseService.findByMonthAndYear(Integer.parseInt(year), Integer.parseInt(month));
        return new ResponseEntity<>(expensesForMonth, HttpStatus.OK);
    }

    @DeleteMapping("/del-expense/{id}")
    public void deleteExpense(@PathVariable("id") Long id) {
        expenseService.deleteExpense(id);
    }

    @PostMapping("/update-expense")
    public ResponseEntity<?> updateExpense(@RequestBody Expense expense) {
        Optional<Expense> expenseData = expenseService.findById(expense.getId());
        if (expenseData.isPresent()) {
            Expense expenseObj = expenseData.get();
            expenseObj.setId(expense.getId());
            expenseObj.setCategory(expense.getCategory());
            expenseObj.setPrice(expense.getPrice());
            expenseObj.setDate(expense.getDate());
            expenseObj.setNote(expense.getNote());
            Expense updateCat = expenseService.saveExpense(expenseObj);
            return new ResponseEntity<>(updateCat, HttpStatus.OK);
        }
        return new ResponseEntity<>(expense, HttpStatus.OK);
    }*/

}
