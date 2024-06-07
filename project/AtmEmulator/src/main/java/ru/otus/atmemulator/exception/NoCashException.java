package ru.otus.atmemulator.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NoCashException extends RuntimeException {
    private static final Logger logger = LoggerFactory.getLogger(NoCashException.class);

    public NoCashException(String message) {
        super(message);
        logger.error(message);
    }
}
