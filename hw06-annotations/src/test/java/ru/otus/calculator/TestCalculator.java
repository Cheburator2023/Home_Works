package ru.otus.calculator;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;
import ru.otus.calculator.Calculator;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
@SuppressWarnings("java:S5838")
public class TestCalculator {
    private Calculator calculator;

    @Before
    public void setCalculator() {
        calculator = new Calculator(5,5);
    }

    @Test
    public void sumCalculator() {
        assertThat(calculator.sum()).isEqualTo(10);
    }

    @Test
    public void substractionCalculator() {
        assertThat(calculator.substraction()).isEqualTo(0);
    }

    @Test
    public void multiplicationCalculator() {
        assertThat(calculator.multiplication()).isEqualTo(25);
    }

    @Test
    public void divisionCalculator() {
        assertThat(calculator.division()).isEqualTo(1);
    }

//    @Test
//    public void divizionByZero() {
//        Throwable throwable = catchThrowable(() -> calculator.setZeroB().division());
//        assertThat(throwable)
//                .isInstanceOf(ArithmeticException.class)
//                .hasMessageContaining("You can't divide by zero");
//    }

    @After
    public void clean() {
        calculator.clean();
    }
}
