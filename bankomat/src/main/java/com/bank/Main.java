package com.bank;

import com.bank.entities.ATM;

/**
 * Главный класс приложения.
 */
public class Main {
    public static void main(String[] args) {
        ATM atm = new ATM();
        atm.run();
    }
}