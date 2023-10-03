package com.aakbmir.expensetracker.entity;

public class MonthlyTotal implements Comparable<MonthlyTotal> {

    private String monthYear;
    private double totalExpense;
    private double totalBudget;

    public MonthlyTotal() {
    }
    public MonthlyTotal(String monthYear, double totalExpense, double totalBudget) {
        this.monthYear = monthYear;
        this.totalExpense = totalExpense;
        this.totalBudget = totalBudget;
    }

    public String getMonthYear() {
        return monthYear;
    }

    public void setMonthYear(String monthYear) {
        this.monthYear = monthYear;
    }

    public double getTotalExpense() {
        return totalExpense;
    }

    public void setTotalExpense(double totalExpense) {
        this.totalExpense = totalExpense;
    }

    public double getTotalBudget() {
        return totalBudget;
    }

    public void setTotalBudget(double totalBudget) {
        this.totalBudget = totalBudget;
    }

    @Override
    public String toString() {
        return "MonthlyTotal{" +
                "monthYear='" + monthYear + '\'' +
                ", totalExpense=" + totalExpense +
                ", totalBudget=" + totalBudget +
                '}';
    }

    @Override
    public int compareTo(MonthlyTotal o) {
        return this.monthYear.compareTo(o.getMonthYear());
    }
}