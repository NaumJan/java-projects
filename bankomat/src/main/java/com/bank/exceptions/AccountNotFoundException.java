package com.bank.exceptions;

/**
 * Исключение, выбрасываемое при отсутствии счета.
 */
public class AccountNotFoundException extends BusinessException {
    public AccountNotFoundException(String accountNumber) {
        super("Account with number " + accountNumber + " not found.");
    }
}
