package ru.otus.tests;

import org.assertj.core.api.AssertionsForClassTypes;
import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;
import ru.otus.calculator.Calculator;

import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;

public class CalculatorTest {

    private Calculator calculator;

    @Before
    public void setCalculator() {
        calculator = new Calculator(5,5);
    }

    @Test
    public void sumCalculator() {
        AssertionsForClassTypes.assertThat(calculator.sum()).isEqualTo(10);
    }

    @Test
    public void substractionCalculator() {
        AssertionsForClassTypes.assertThat(calculator.substraction()).isEqualTo(0);
    }

    @Test
    public void multiplicationCalculator() {
        AssertionsForClassTypes.assertThat(calculator.multiplication()).isEqualTo(25);
    }

    @Test
    public void divisionCalculator() {
        AssertionsForClassTypes.assertThat(calculator.division()).isEqualTo(1);
    }

//    @Test
//    public void divizionByZero() {
//        Throwable throwable = catchThrowable(() -> calculator.setZeroB().division());
//        AssertionsForClassTypes.assertThat(throwable)
//                .isInstanceOf(ArithmeticException.class)
//                .hasMessageContaining("You can't divide by zero");
//    }

    @After
    public void clean() {
        calculator.clean();
    }
}
