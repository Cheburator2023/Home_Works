package ru.otus;

import ru.otus.proxy.Ioc;
import ru.otus.test.TestLogging;
import ru.otus.test.TestLoggingInterface;

public class Main {
    public static void main(String[] args) {
        final Class<TestLoggingInterface> loggingInterfaceClass = TestLoggingInterface.class;

        final TestLoggingInterface testLogging = Ioc.createTestLogging(loggingInterfaceClass, new TestLogging());
        testLogging.calculation(5);
        testLogging.calculation(8, 14);
        testLogging.calculation(29, 9872, "Тестовое сообщение (testLogging)");
    }
}