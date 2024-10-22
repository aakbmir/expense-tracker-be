package com.aakbmir.expensetracker.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static java.lang.Thread.sleep;

@Service
public class TestService {
    private final RestTemplate restTemplate;

    public TestService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String callThirdPartyApi() {
        try {
            sleep(20000);
            String apiUrl = "https://api.thirdparty.com/data"; // Replace with actual API URL
            return "Sedniffn response";
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
