package com.bank.exceptions;

/**
 * Исключение, выбрасываемое при неверной транзакции.
 */
public class InvalidTransactionException extends BusinessException {
    public InvalidTransactionException(String message) {
        super(message);
    }
}