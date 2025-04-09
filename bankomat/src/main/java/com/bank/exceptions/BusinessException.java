package com.bank.exceptions;

/**
 * Базовый класс для исключений бизнес-логики.
 */
public class BusinessException extends Exception {
    public BusinessException(String message) {
        super(message);
    }
}