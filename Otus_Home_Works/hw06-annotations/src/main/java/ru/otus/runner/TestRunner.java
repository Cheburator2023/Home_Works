package ru.otus.runner;

import lombok.Data;
import lombok.SneakyThrows;
import result.Result;
import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;
import ru.otus.tests.TestClass;

@Data
public class TestRunner {

    @SneakyThrows
    public void runTest() {
        Class<?> testClass = Class.forName("ru.otus.tests.CalculatorTest");
        invokeClass(testClass);

    }

    private void invokeClass(Class<?> clazz) {
        var testClass = TestClass.set(clazz);



        testClass.invokeMethods(Before.class);
        Result result = new Result();
        result.results(testClass.invokeMethods(Test.class));
        testClass.invokeMethods(After.class);
    }
}
