package ru.otus.atmemulator.exception;

public class BanknoteNotFoundException extends RuntimeException {
    public BanknoteNotFoundException(String message) {
        super(message);
    }
}