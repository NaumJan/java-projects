package com.bank.entities;
import com.bank.exceptions.AccountNotFoundException;

import java.util.*;
/**
 * Класс для управления банковскими счетами.
 */
public class AccountManager {
    private final Map<String, Account> accounts;

    /**
     * Конструктор инициализирует хранилище счетов.
     */
    public AccountManager() {
        this.accounts = new HashMap<>();
    }

    /**
     * Создает новый счет.
     *
     * @param accountNumber Номер счета
     * @return true, если счет создан успешно; false, если счет уже существует
     */
    public boolean createAccount(String accountNumber) {
        if (accounts.containsKey(accountNumber)) {
            return false;
        }
        accounts.put(accountNumber, new Account(accountNumber));
        return true;
    }

    /**
     * Получает счет по номеру.
     *
     * @param accountNumber Номер счета
     * @return Объект Account
     * @throws AccountNotFoundException Если счет не найден
     */
    public Account getAccount(String accountNumber) throws AccountNotFoundException {
        Account account = accounts.get(accountNumber);
        if (account == null) {
            throw new AccountNotFoundException(accountNumber);
        }
        return account;
    }
}