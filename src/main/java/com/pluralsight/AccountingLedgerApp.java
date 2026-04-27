package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
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
//                case 3:
//                    ledger();
//                    break;
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
        double deposit = 0.00;
        String name = "";
        String cardNum = "";
        String date = "";
        String cvv = "";
        String zip = "";

        while (run) {
            System.out.println("\t1 Enter Deposit Amount");
            System.out.println("\t2 Enter Debit Card Information");
            System.out.println("\t3 Submit Deposit");
            System.out.println("\t4 Cancel/Go Back");
            System.out.print("Enter your choice here: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter Deposit Amount: $");
                    deposit = sc.nextDouble();
                    sc.nextLine();
                    break;
                case 2:
                    System.out.println("Enter Debit Card Information");
                    System.out.print("\t Name on Card: ");
                    name = sc.nextLine().trim();
                    while (true) {
                        System.out.print("\t Card Number (16-Digits): ");
                        cardNum = sc.nextLine().trim();

                        if (cardNum.matches("\\d{16}")) {
                            break;
                        }
                        System.out.println("Invalid card number. Must be 16 digits");
                    }
                    System.out.print("\t Expiration Date (MM/YY): ");
                    date = sc.nextLine();
                    System.out.print("\t CVV (3-Digits): ");
                    cvv = sc.nextLine();
                    System.out.print("\t Zip-Code (5-Digits): ");
                    zip = sc.nextLine();
                    break;
                case 3:
                    System.out.println("Deposit Amount: $" + deposit);
                    System.out.println("Card number ending in " + cardNum.substring(12));
                    System.out.println("Expiration: " + date);
                    System.out.println("Zip-Code: " + zip);

                    System.out.println("Confirm Deposit (Y/N)");
                    saveTransactions("Deposit", name, deposit);
                    String confirmation = sc.nextLine().trim();

                    if (confirmation.equalsIgnoreCase("Y")) {
                        System.out.println("Deposit Successful");
                    } else {
                        System.out.println("Deposit Cancelled");
                    }
                    break;
                case 4:
                    run = false;
                    break;
            }

        }

    }

    // make payment screen
    public static void makePayment() {
        boolean run = true;
        double payment = 0.00;
        String name = "";
        String cardNum = "";
        String date = "";
        String cvv = "";
        String zip = "";

        while (run) {
            System.out.println("\t1 Enter Payment Amount");
            System.out.println("\t2 Enter Debit Card Information");
            System.out.println("\t3 Submit Payment");
            System.out.println("\t4 Cancel/Go Back");
            System.out.print("Enter your choice here: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter Payment Amount: $");
                    payment = sc.nextDouble();
                    sc.nextLine();
                    break;
                case 2:
                    System.out.println("Enter Debit Card Information");
                    System.out.print("\t Name on Card: ");
                    name = sc.nextLine().trim();
                    while (true) {
                        System.out.print("\t Card Number (16-Digits): ");
                        cardNum = sc.nextLine().trim();

                        if (cardNum.matches("\\d{16}")) {
                            break;
                        }
                        System.out.println("Invalid card number. Must be 16 digits");
                    }
                    System.out.print("\t Expiration Date (MM/YY): ");
                    date = sc.nextLine();
                    System.out.print("\t CVV (3-Digits): ");
                    cvv = sc.nextLine();
                    System.out.print("\t Zip-Code (5-Digits): ");
                    zip = sc.nextLine();
                    break;
                case 3:
                    System.out.println("Payment Amount: $" + payment);
                    System.out.println("Card number ending in " + cardNum.substring(12));
                    System.out.println("Expiration: " + date);
                    System.out.println("Zip-Code: " + zip);

                    System.out.println("Confirm Payment (Y/N): ");
                    String confirmation = sc.nextLine().trim();

                    if (confirmation.equalsIgnoreCase("Y")) {
                        saveTransactions("Payment", name, payment);
                        System.out.println("Payment Successful");
                    } else {
                        System.out.println("Payment Cancelled");
                    }
                    break;
                case 4:
                    run = false;
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
        boolean run = true;

        while (run) {
            System.out.println("\t (A) All Entries");
            System.out.println("\t (D) Deposits Only");
            System.out.println("\t (P) Payments Only");
            System.out.println("\t (R) Reports");
            System.out.println("\t (H) Home Screen");
            System.out.print("Enter your choice here: ");

        }
    }
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
}
