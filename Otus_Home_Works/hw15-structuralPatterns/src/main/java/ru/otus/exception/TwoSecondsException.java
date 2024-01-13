package ru.otus.exception;

public class TwoSecondsException extends RuntimeException{
    public TwoSecondsException(String message) {
        super(message);
    }

    public TwoSecondsException(Throwable throwable) {
        super(throwable);
    }
}
