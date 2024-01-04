package ru.otus.test;

import ru.otus.annotations.Log;

public class TestLogging implements TestLoggingInterface {
    @Override
    @Log
    public void calculation(int param) {
        System.out.printf("executed method: calculation, param: %d", param);
    }
}
