package com.bank.entities;
import com.bank.exceptions.InvalidTransactionException;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс, представляющий банковский счет.
 */
public class Account {
    private final String accountNumber;
    private double balance;
    private final List<String> transactionHistory;

    /**
     * Конструктор для создания счета.
     *
     * @param accountNumber Номер счета
     */
    public Account(String accountNumber) {
        this.accountNumber = accountNumber;
        this.balance = 0.0;
        this.transactionHistory = new ArrayList<>();
    }

    /**
     * Возвращает номер счета.
     *
     * @return Номер счета
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * Возвращает текущий баланс счета.
     *
     * @return Баланс
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Пополняет счет.
     *
     * @param amount Сумма пополнения
     * @throws InvalidTransactionException Если сумма отрицательная
     */
    public void deposit(double amount) throws InvalidTransactionException {
        if (amount <= 0) {
            throw new InvalidTransactionException("Deposit amount must be positive");
        }
        balance += amount;
        transactionHistory.add("Deposited: " + amount);
    }

    /**
     * Снимает деньги со счета.
     *
     * @param amount Сумма снятия
     * @throws InvalidTransactionException Если сумма отрицательная или недостаточно средств
     */
    public void withdraw(double amount) throws InvalidTransactionException {
        if (amount <= 0) {
            throw new InvalidTransactionException("Withdrawal amount must be positive");
        }
        if (amount > balance) {
            throw new InvalidTransactionException("Insufficient funds");
        }
        balance -= amount;
        transactionHistory.add("Withdrew: " + amount);
    }

    /**
     * Возвращает историю транзакций.
     *
     * @return Список строк с операциями
     */
    public List<String> getTransactionHistory() {
        return new ArrayList<>(transactionHistory);
    }
}
