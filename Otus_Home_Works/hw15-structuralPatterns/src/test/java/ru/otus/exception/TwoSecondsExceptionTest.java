package ru.otus.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.otus.exception.TwoSecondsException;
import ru.otus.model.Message;
import ru.otus.processor.ProcessorTwoSecondsException;

import java.time.LocalDateTime;
import java.util.function.Supplier;

public class TwoSecondsExceptionTest {

    @Test
    public void testProcess_ThrowsTwoSecondsException() {
        Supplier<LocalDateTime> localDateTimeSupplier = LocalDateTime::now;
        ProcessorTwoSecondsException processor = new ProcessorTwoSecondsException(localDateTimeSupplier);
        Message message = new Message.Builder(1L).build();

        Assertions.assertThrows(TwoSecondsException.class, () -> processor.process(message));
    }

    @Test
    public void testProcess_ReturnsMessage() {
        Supplier<LocalDateTime> localDateTimeSupplier = () -> LocalDateTime.now().withSecond(1);
        ProcessorTwoSecondsException processor = new ProcessorTwoSecondsException(localDateTimeSupplier);
        Message message = new Message.Builder(1L).build();

        Message result = processor.process(message);

        Assertions.assertEquals(message, result);
    }
}
