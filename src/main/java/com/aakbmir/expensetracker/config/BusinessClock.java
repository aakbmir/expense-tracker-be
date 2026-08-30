package com.aakbmir.expensetracker.config;

import org.springframework.stereotype.Component;

import java.time.*;

@Component
public class BusinessClock {

    public static final ZoneId BUSINESS_ZONE =
            ZoneId.of("Asia/Dubai");

    private final Clock clock;

    public BusinessClock() {
        this.clock = Clock.systemUTC();
    }

    public Instant now() {
        return Instant.now(clock);
    }

    public ZonedDateTime nowGST() {
        return now().atZone(BUSINESS_ZONE);
    }

    public LocalDate today() {
        return nowGST().toLocalDate();
    }

    public YearMonth currentMonth() {
        return YearMonth.from(nowGST());
    }

    public ZoneId zone() {
        return BUSINESS_ZONE;
    }

    public Instant startOfDay(LocalDate date) {
        return date
                .atStartOfDay(BUSINESS_ZONE)
                .toInstant();
    }

    public Instant startOfMonth(YearMonth month) {
        return month
                .atDay(1)
                .atStartOfDay(BUSINESS_ZONE)
                .toInstant();
    }

    public Instant startOfNextMonth(YearMonth month) {
        return month
                .plusMonths(1)
                .atDay(1)
                .atStartOfDay(BUSINESS_ZONE)
                .toInstant();
    }
}