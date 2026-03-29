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
        if (income != null && income.getDate() != null
                && income.getNote() != null && income.getPrice() != 0) {
            Income cat = incomeService.saveIncome(income);
            return new ResponseEntity(cat, HttpStatus.OK);
        } else {
            JSONObject json = new JSONObject().put("message", "invalid Request");
            return new ResponseEntity(json.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-income")
    private ResponseEntity getIncome(@RequestParam(name = "incomeName") String incomeName) {
        List<Income> income = incomeService.findByCategory(incomeName);
        return new ResponseEntity(income, HttpStatus.OK);
    }

    @GetMapping("/get-current-income")
    public ResponseEntity getCurrentIncome(@RequestParam(name = "month") String month, @RequestParam(name = "year") String year) {
        List<Income> incomesForMonth = incomeService.findByMonthAndYear(Integer.valueOf(year), Integer.valueOf(month));
        return new ResponseEntity(incomesForMonth, HttpStatus.OK);
    }

    @DeleteMapping("/del-income/{id}")
    private void deleteIncome(@PathVariable("id") Long id) {
        incomeService.deleteIncome(id);
    }

    @PostMapping("/update-income")
    public ResponseEntity updateIncome(@RequestBody Income income) {
        Optional<Income> incomeData = incomeService.findById(income.getId());
        if (incomeData.isPresent()) {
            Income incomeObj = incomeData.get();
            incomeObj.setId(income.getId());
            incomeObj.setCategory(income.getCategory());
            incomeObj.setPrice(income.getPrice());
            incomeObj.setDate(income.getDate());
            incomeObj.setNote(income.getNote());
            Income updateCat = incomeService.saveIncome(incomeObj);
            return new ResponseEntity(updateCat, HttpStatus.OK);
        }
        return new ResponseEntity(income, HttpStatus.OK);
    }
}
