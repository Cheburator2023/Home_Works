package ru.otus.demo;

import ru.otus.test.TestLoggingInterface;

public class Demo {

    TestLoggingInterface logging;

    public Demo(TestLoggingInterface logging) {
        this.logging = logging;
    }

    public void action() {
        logging.calculation(6);
    }
}
