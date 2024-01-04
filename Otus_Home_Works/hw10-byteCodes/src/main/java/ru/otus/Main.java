package ru.otus;

import ru.otus.demo.Demo;
import ru.otus.test.Ioc;

public class Main {
    public static void main(String[] args) {
        var testlogging = Ioc.createTestLogging();
        var demo = new Demo(testlogging);
        demo.action();
    }
}