package com.bank.entities;
import com.bank.exceptions.AccountNotFoundException;
import com.bank.exceptions.BusinessException;

import java.util.*;

/**
 * Класс, реализующий работу банкомата.
 */
public class ATM {
    private final AccountManager accountManager;
    private final Scanner scanner;

    /**
     * Конструктор банкомата.
     */
    public ATM() {
        this.accountManager = new AccountManager();
        this.scanner = new Scanner(System.in);
    }

    /**
     * Запускает работу банкомата.
     */
    public void run() {
        while (true) {
            try {
                System.out.println("\nATM Menu:");
                System.out.println("1. Create Account");
                System.out.println("2. View Balance");
                System.out.println("3. Deposit");
                System.out.println("4. Withdraw");
                System.out.println("5. Transaction History");
                System.out.println("6. Exit");
                System.out.print("Choose an option: ");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1 -> createAccount();
                    case 2 -> viewBalance();
                    case 3 -> deposit();
                    case 4 -> withdraw();
                    case 5 -> viewTransactionHistory();
                    case 6 -> {
                        System.out.println("Exiting...");
                        return;
                    }
                    default -> System.out.println("Invalid option. Try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: Invalid input. Please try again.");
                scanner.nextLine(); // очистка ввода
            }
        }
    }

    private void createAccount() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();
        if (accountManager.createAccount(accountNumber)) {
            System.out.println("Account created successfully.");
        } else {
            System.out.println("Error: Account already exists.");
        }
    }

    private void viewBalance() {
        try {
            Account account = getAccount();
            System.out.println("Balance: " + account.getBalance());
        } catch (BusinessException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void deposit() {
        try {
            Account account = getAccount();
            System.out.print("Enter amount to deposit: ");
            double amount = scanner.nextDouble();
            scanner.nextLine();
            account.deposit(amount);
            System.out.println("Deposited successfully.");
        } catch (BusinessException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void withdraw() {
        try {
            Account account = getAccount();
            System.out.print("Enter amount to withdraw: ");
            double amount = scanner.nextDouble();
            scanner.nextLine();
            account.withdraw(amount);
            System.out.println("Withdrawn successfully.");
        } catch (BusinessException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewTransactionHistory() {
        try {
            Account account = getAccount();
            System.out.println("Transaction History:");
            for (String transaction : account.getTransactionHistory()) {
                System.out.println(transaction);
            }
        } catch (BusinessException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private Account getAccount() throws AccountNotFoundException {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();
        return accountManager.getAccount(accountNumber);
    }
}