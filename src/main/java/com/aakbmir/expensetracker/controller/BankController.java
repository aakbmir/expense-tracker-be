package com.aakbmir.expensetracker.controller;

import com.aakbmir.expensetracker.entity.Bank;
import com.aakbmir.expensetracker.service.BankService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/bank")
@CrossOrigin("*")
public class BankController {

    @Autowired
    BankService bankService;

    @PostMapping("/save-bank-record")
    public ResponseEntity saveBank(@RequestBody Bank bank) {
        if (bank != null && bank.getDate() != null && bank.getName() != null) {
            Bank bankObj = bankService.saveBankRecord(bank);
            return new ResponseEntity(bankObj, HttpStatus.OK);
        } else {
            JSONObject json = new JSONObject().put("message", "invalid Request");
            return new ResponseEntity(json.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-bank-record")
    private ResponseEntity getBank(@RequestParam(name = "bankName") String bankName) {
        List<Bank> bank = bankService.findByName(bankName);
        return new ResponseEntity(bank, HttpStatus.OK);
    }

    @GetMapping("/get-current-bank-record")
    public ResponseEntity getCurrentBank(@RequestParam(name = "month") String month, @RequestParam(name = "year") String year) {
        List<Bank> banksForMonth = bankService.findByMonthAndYear(Integer.valueOf(year), Integer.valueOf(month));
        return new ResponseEntity(banksForMonth, HttpStatus.OK);
    }

    @DeleteMapping("/del-bank-record/{id}")
    private void deleteBank(@PathVariable("id") Long id) {
        bankService.deleteBankRecord(id);
    }

    @PostMapping("/update-bank-record")
    public ResponseEntity updateBank(@RequestBody Bank bank) {
        Optional<Bank> bankData = bankService.findById(bank.getId());
        if (bankData.isPresent()) {
            Bank bankObj = bankData.get();
            bankObj.setId(bank.getId());
            bankObj.setPrice(bank.getPrice());
            bankObj.setDate(bank.getDate());
            Bank updateCat = bankService.saveBankRecord(bankObj);
            return new ResponseEntity(updateCat, HttpStatus.OK);
        }
        return new ResponseEntity(bank, HttpStatus.OK);
    }
}
