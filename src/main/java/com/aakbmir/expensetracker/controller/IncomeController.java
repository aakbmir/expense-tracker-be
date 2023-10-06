package com.aakbmir.expensetracker.controller;

import com.aakbmir.expensetracker.entity.Income;
import com.aakbmir.expensetracker.service.IncomeService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/income")
@CrossOrigin("*")
public class IncomeController {

    @Autowired
    IncomeService incomeService;

    @PostMapping("/save-income")
    public ResponseEntity saveIncome(@RequestBody Income income) {
        if (income != null && income.getName() != null) {
            Income cat = incomeService.saveIncome(income);
            return new ResponseEntity(cat, HttpStatus.OK);
        } else {
            JSONObject json = new JSONObject().put("message", "invalid Request");
            return new ResponseEntity(json.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/save-mul-income")
    public ResponseEntity saveMulIncome(@RequestBody List<Income> incomeList) {
        for (Income cat : incomeList) {
            incomeService.saveIncome(cat);
        }
        return new ResponseEntity("Success", HttpStatus.OK);
    }

    @GetMapping("/get-income/{incomeName}")
    private ResponseEntity getIncome(@PathVariable("incomeName") String incomeName) {
        Income cat = incomeService.findByName(incomeName);
        return new ResponseEntity(cat, HttpStatus.OK);
    }

/*
    @GetMapping("/get-current-income")
    public ResponseEntity getCurrentIncome(@RequestParam(name = "month") String month, @RequestParam(name = "year") String year) {
        List<Income> incomesForMonth = incomeService.findByMonthAndYear(Integer.valueOf(year), Integer.valueOf(month));
        return new ResponseEntity(incomesForMonth, HttpStatus.OK);
    }
*/

    @GetMapping("/get-all-incomes")
    public ResponseEntity getAllIncome() {
        List<Income> catList = incomeService.getAllIncomes();
        return new ResponseEntity(catList, HttpStatus.OK);
    }

    @DeleteMapping("/del-income/{id}")
    private void deleteIncome(@PathVariable("id") Long id) {
        incomeService.deleteIncome(id);
    }

    @PostMapping("/update-income")
    public ResponseEntity updateIncome(@RequestBody Income income) {
        Optional<Income> incomeObj = incomeService.findById(income.getId());

        if (incomeObj.isPresent()) {
            Income cat = incomeObj.get();
            cat.setId(income.getId());
            cat.setName(income.getName());
            Income updateCat = incomeService.updateIncome(cat);
            return new ResponseEntity(updateCat, HttpStatus.OK);
        }
        return new ResponseEntity(income, HttpStatus.OK);
    }

}
