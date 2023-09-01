package com.aakbmir.expensetracker.controller;

import com.aakbmir.expensetracker.entity.Income;
import com.aakbmir.expensetracker.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/income")
@CrossOrigin("*")
public class IncomeController {

    @Autowired
    IncomeService incomeService;

    @PostMapping("/save-income")
    public ResponseEntity saveIncome(@RequestBody Income income) {
        Income cat = incomeService.saveIncome(income);
        return new ResponseEntity(cat, HttpStatus.OK);
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
        Income cat = incomeService.findByType(incomeName);
        return new ResponseEntity(cat, HttpStatus.OK);
    }

    @GetMapping("/get-all-income")
    public ResponseEntity getIncome() {
        List<Income> catList = incomeService.getAllIncome();
        return new ResponseEntity(catList, HttpStatus.OK);
    }

    @DeleteMapping("/del-income/{id}")
    private void deleteIncome(@PathVariable("id") Long id) {
        incomeService.deleteIncome(id);
    }

    @PostMapping("/update-income")
    public ResponseEntity updateIncome(@RequestBody Income income) {
        Income cat = incomeService.findByType(income.getType());
        Income updateCat = incomeService.updateIncome(cat);
        return new ResponseEntity(updateCat, HttpStatus.OK);
    }

/*    @GetMapping("/filter-income")
    public ResponseEntity filterIncome(@RequestParam(name = "month") String month, @RequestParam(name = "year") String year) {
        System.out.println(month);
        System.out.println(year);
        List<Income> catList = incomeService.filterIncome(month, year);
        return new ResponseEntity(catList, HttpStatus.OK);
    }*/
}
