package ru.otus.runner;

import lombok.Data;
import lombok.SneakyThrows;
import ru.otus.result.Result;
import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;
import ru.otus.tests.TestClass;

@Data
public class TestRunner {

    @SneakyThrows
    public void runTest() {
        Class<?> calculatorTestClass = Class.forName("ru.otus.tests.CalculatorTest");
        var testClass = new TestClass(calculatorTestClass);
        var result = new Result();
        result.results(testClass.invokeMethods(Test.class));
    }
}
