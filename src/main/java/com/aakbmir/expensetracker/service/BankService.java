package com.aakbmir.expensetracker.service;

import com.aakbmir.expensetracker.entity.Bank;
import com.aakbmir.expensetracker.repository.BankRepository;
import com.aakbmir.expensetracker.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BankService {

    @Autowired
    BankRepository bankRepository;

    @Autowired
    CommonUtils commonUtils;

    public Bank saveBankRecord(Bank bankRecord) {
        return bankRepository.save(bankRecord);
    }

    public List<Bank> findByName(String bankRecord) {
        if(bankRecord == null || bankRecord.equalsIgnoreCase("")) {
            return bankRepository.findAll();
        } else {
            return bankRepository.findByName(bankRecord);
        }
    }

    public Optional<Bank> findById(Long id) {
        return bankRepository.findById(id);
    }

    public void deleteBankRecord(Long id) {
        bankRepository.deleteById(id);
    }

    public List<Bank> findByMonthAndYear(int year, int month) {
        return bankRepository.findByMonthAndYear(year, month);
    }
}