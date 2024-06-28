package ru.otus.atmemulator.exception;

public class ATMNotFoundException extends RuntimeException {
    public ATMNotFoundException(String message) {
        super(message);
    }
}