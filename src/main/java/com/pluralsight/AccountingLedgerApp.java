package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class AccountingLedgerApp {
    static Scanner sc = new Scanner(System.in);
    static ArrayList<Transactions> list = new ArrayList<>();

    public static void main(String[] args) {
    // home screen
        boolean run = true;

        while (run) {
            System.out.println("Welcome to the Accounting Ledger");
            System.out.println("What are you here for today?");
            System.out.println("\t1 Making a Deposit");
            System.out.println("\t2 Making a Payment (Debit Only)");
            System.out.println("\t3 Ledger");
            System.out.println("\t4 Exit");
            System.out.print("Enter your choice here: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    makeDeposit();
                    break;
                case 2:
                    makePayment();
                    break;
               case 3:
                   ledger();
                    break;
                case 4:
                    System.out.println("Thank You for using the Account Ledger! Goodbye");
                    run = false;
                    break;
                default:
                    System.out.println("Invalid selection; Please try again");
            }

        }

    }

    // make deposits screen
    public static void makeDeposit() {
        boolean run = true;

        while (run) {
            System.out.print("Enter Deposit Amount: $");
            double deposit = sc.nextDouble();
            sc.nextLine();

            System.out.print("Confirm Deposit (Y/N): ");
            String confirm = sc.nextLine();

            if (confirm.equalsIgnoreCase("Y")) {
                saveTransactions("Deposit", "Self", deposit);
                System.out.println("Deposit Successful");
            } else {
                System.out.println("Deposit Cancelled");
            }
            System.out.println("Would you like to:");
            System.out.println("\t 1 Make another deposit");
            System.out.println("\t 2 Go Back to Home Screen");
            System.out.print("Enter your choice here: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("\n Making another deposit");
                    break;
                case 2:
                    System.out.println("Returning to Home Screen");
                    run = false;
                    break;
                default:
                    System.out.println("Invalid selection; Please try again");
                    break;


            }

        }

    }

    // make payment screen
    public static void makePayment() {
        boolean run = true;

        while (run) {
            System.out.print("Enter Payment Amount: $");
            double payment = sc.nextDouble();
            sc.nextLine();
            System.out.print("Enter a Payment Description: ");
            String description = sc.nextLine();
            System.out.print("Enter Vendor Name:");
            String vendor = sc.nextLine();
            System.out.println("Debit Card Information");

            String cardNum = "";
            while (true) {
                System.out.print("Enter Card Number (16-Digits): ");
                cardNum = sc.nextLine().trim();
                if (cardNum.matches("\\d{16}"))
                    break;
                System.out.println("Invalid card number. Must be 16 digits");
            }
            System.out.print("Expiration date: ");
            String date = sc.nextLine();

            System.out.print("CVV 3 Digits: ");
            String cvv = sc.nextLine();

            System.out.print("Zip-Code: ");
            String zip = sc.nextLine();

            System.out.println("Description: " + description);
            System.out.println("Vendor: " + vendor);
            System.out.println("Payment Amount: $" + payment);
            System.out.println("Card Ending in " + cardNum.substring(12));
            System.out.println("Expiration: " + date);
            System.out.println("Zip-Code: " + zip);

            System.out.print("Confirm Payment (Y/N): ");
            String confirm = sc.nextLine();

            if (confirm.equalsIgnoreCase("Y")) {
                saveTransactions(description, vendor, -payment);
                System.out.println("Payment Successful");
            } else {
                System.out.println("Payment Cancelled");
            }
            System.out.println("Would you like to:");
            System.out.println("\t 1 Make another Payment");
            System.out.println("\t 2 Go Back to Home Screen");
            System.out.print("Enter your choice here: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("\n Making another payment");
                    break;
                case 2:
                    System.out.println("Returning to Home Screen");
                    run = false;
                    break;
                default:
                    System.out.println("Invalid selection; Please try again");
                    break;
            }
        }
    }

    // save transactions
    public static void saveTransactions(String description, String vendor, double amount) {
        try (
                FileWriter filewriter = new FileWriter("transactions.csv", true);
                BufferedWriter writer = new BufferedWriter(filewriter)
                ) {
                LocalDate date = LocalDate.now();
                LocalTime time = LocalTime.now().withNano(0);

                String csvline = date + ", " + time + ", " + description + ", " + vendor + ", " + amount;

                writer.write(csvline);
                writer.newLine();

        } catch (IOException e) {
            System.out.println("Error saving transaction" + e.getMessage());
        }
    }
    // ledger screen
    public static void ledger() {
        ArrayList<Transactions> list = loadTransactions();
        Collections.reverse(list);
        boolean run = true;

        while (run) {
            System.out.println("\t (A) All Entries");
            System.out.println("\t (D) Deposits Only");
            System.out.println("\t (P) Payments Only");
            System.out.println("\t (R) Reports");
            System.out.println("\t (H) Home Screen");
            System.out.print("Enter your choice here: ");
            String choice = sc.nextLine().trim().toUpperCase();

            switch (choice) {
                case "A":
                    showAllEntries(list);
                    break;
                case "D":
                    showDeposits(list);
                    break;
                case "P":
                    showPayments(list);
                    break;
                case "R":
                    showReports(list);
                case "H":
                    System.out.println("Exiting to Home Screen");
                    run = false;
                    break;
                default:
                    System.out.println("Invalid selection; Please try again");
            }

        }
    }
    // load transactions method
    public static ArrayList<Transactions> loadTransactions() {

        try(BufferedReader reader = new BufferedReader(new FileReader("transactions.csv"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String [] parts = line.split("\\,");
                String date = parts[0];
                String time = parts [1];
                String description = parts[2];
                String vendor = parts[3];
                double amount = Double.parseDouble(parts[4]);

                list.add(new Transactions(date, time, description, vendor, amount));

            }
        } catch (Exception e) {
            System.out.println("Error loading transactions" + e.getMessage());
        }
          return list;
    }
    // ledger options
    public static void showAllEntries(ArrayList<Transactions> list) {
        for (Transactions entry : list) {
            System.out.println(entry.date + ", " + entry.time + ", " + entry.description +
                    ", " + entry.vendor + ", " + entry.amount);
        }

    }
    public static void showDeposits(ArrayList<Transactions> list) {
        for (Transactions entry : list) {
            if (entry.amount > 0) {
                System.out.println(entry.date + ", " + entry.time + ", " + entry.description +
                        ", " + entry.vendor + ", " + entry.amount);
            }
        }
    }
    public static void showPayments(ArrayList<Transactions> list) {
        for (Transactions entry : list) {
            if (entry.amount < 0) {
                System.out.println(entry.date + ", " + entry.time + ", " + entry.description +
                        ", " + entry.vendor + ", " + entry.amount);
            }
        }
    }
    public static void showReports(ArrayList<Transactions> list){
        boolean run = true;

        while (run) {

            System.out.println("\t1 Month to Date");
            System.out.println("\t2 Previous Month");
            System.out.println("\t3 Year to Date");
            System.out.println("\t4 Previous Year");
            System.out.println("\t5 Search by Name");
            System.out.println("\t6 Go Back/Cancel");
            System.out.print("Enter you choice here: ");
            int choice = sc.nextInt();
            sc.nextLine();
        }
    }
}
