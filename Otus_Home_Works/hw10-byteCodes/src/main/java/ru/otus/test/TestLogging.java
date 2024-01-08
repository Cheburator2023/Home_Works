package ru.otus.test;

import ru.otus.annotations.Log;

public class TestLogging implements TestLoggingInterface {

    @Log
    @Override
    public void calculation(int param) {
        System.out.println("\tClass TestLogging, Method: calculation(int param)");
    }

    @Log
    @Override
    public void calculation(int paramOne, int paramTwo) {
        System.out.println("\tClass TestLogging, Method: calculation(int paramOne, int paramTwo)");
    }

    @Log
    @Override
    public void calculation(int paramOne, int paramTwo, String paramThree) {
        System.out.println("\tClass TestLogging, Method: calculation(int paramOne, int paramTwo, String paramThree)");
    }
}
