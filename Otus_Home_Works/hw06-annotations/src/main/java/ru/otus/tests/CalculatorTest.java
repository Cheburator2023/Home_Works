package ru.otus.tests;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;
import ru.otus.calculator.Calculator;

import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;

public class CalculatorTest {

    private final Logger logger = LoggerFactory.getLogger(TestClass.class);

    private Calculator calculator;

    @Before
    public void setCalculator() {
        calculator = new Calculator(5,5);
        logger.info(()->"before");
    }

    @Test
    public void sumCalculator() {
        AssertionsForClassTypes.assertThat(calculator.sum()).isEqualTo(10);
        logger.info(()->"sumCalculator " + calculator.getA() + " " + calculator.getB());
    }

    @Test
    public void substractionCalculator() {
        AssertionsForClassTypes.assertThat(calculator.substraction()).isEqualTo(0);
        logger.info(()->"substractionCalculator " + calculator.getA() + " " + calculator.getB());
    }

    @Test
    public void multiplicationCalculator() {
        AssertionsForClassTypes.assertThat(calculator.multiplication()).isEqualTo(25);
        logger.info(()->"multiplicationCalculator " + calculator.getA() + " " + calculator.getB());
    }

    @Test
    public void divisionCalculator() {
        AssertionsForClassTypes.assertThat(calculator.division()).isEqualTo(1);
        logger.info(()->"divisionCalculator " + calculator.getA() + " " + calculator.getB());
    }

    @Test
    public void divizionByZero() {
        logger.info(()->"divizionByZero1 " + calculator.getA() + " " + calculator.getB());

        Throwable throwable = catchThrowable(() -> calculator.setZeroB().division());
        AssertionsForClassTypes.assertThat(throwable)
                .isInstanceOf(ArithmeticException.class)
                .hasMessageContaining("You can't divide by zero");
        logger.info(()->"divizionByZero2 " + calculator.getA() + " " + calculator.getB());

    }

    @After
    public void clean() {
//        logger.info(()->"clean1 " + calculator.getA() + " " + calculator.getB());
        calculator.clean();
        logger.info(()->"clean2 " + calculator.getA() + " " + calculator.getB());

    }
}
