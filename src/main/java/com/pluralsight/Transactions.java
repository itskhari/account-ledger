package com.pluralsight;

public class Transactions {
    String date;
    String time;
    String description;
    String vendor;
    double amount;

    public Transactions(String date, String time, String description, String vendor, double amount) {
        this.date = date;
        this.time = time;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }
}
