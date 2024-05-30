package ru.otus.atmemulator.exception;

public class NoCashException extends RuntimeException{

    public NoCashException() {
    }

    public NoCashException(String message) {
        System.out.println(message);
    }
}
