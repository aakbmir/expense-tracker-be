package com.aakbmir.expensetracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component

public class TestController {

    @Autowired
    ExpenseController expenseController;

    @Scheduled(fixedRate = 10000)
    public void reportCurrentTime() {
        expenseController.getStatus();
    }
}