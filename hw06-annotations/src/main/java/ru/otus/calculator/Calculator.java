package ru.otus.calculator;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class Calculator {

    private int a;
    private int b;

    public void clean() {
        this.a = 0;
        this.b = 0;
    }

    public int sum() {
        return a + b;
    }

    public int substraction() {
        return a - b;
    }

    public int multiplication() {
        return a * b;
    }

    public int division() {
        return a / b;
    }

    public Calculator setZeroB() {
        this.b = 0;
        return this;
    }
}
