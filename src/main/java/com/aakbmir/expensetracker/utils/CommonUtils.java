package com.aakbmir.expensetracker.utils;

import java.time.LocalDate;
import java.time.Month;
import java.util.Date;
import java.util.List;

public class CommonUtils {
    public static List<String> getYears() {
        return List.of(new String[]{"2023"});
    }

    public static List<String> getMonths() {
        return List.of(new String[]{
                "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"});
    }

    public static String getCurrentMonth() {
        LocalDate currentDate = LocalDate.now();

        Month currentMonth = currentDate.getMonth();
        switch (currentMonth.toString()) {
            case "JANUARY":
                return "Jan";
            case "FEBRUARY":
                return "Feb";
            case "MARCH":
                return "Mar";
            case "APRIL":
                return "Apr";
            case "MAY":
                return "May";
            case "JUNE":
                return "Jun";
            case "JULY":
                return "Jul";
            case "AUGUST":
                return "Aug";
            case "SEPTEMBER":
                return "Sep";
            case "OCTOBER":
                return "Oct";
            case "NOVEMBER":
                return "Nov";
            case "DECEMBER":
                return "Dec";
            default:
                return "";
        }
    }

    public static int getCurrentYear() {
        LocalDate currentDate = LocalDate.now();
        return currentDate.getYear();
    }
}
