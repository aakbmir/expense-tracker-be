package com.aakbmir.expensetracker.controller;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component

public class TestController {
    @Scheduled(fixedRate = 10000)
    public void reportCurrentTime() {

    }
}