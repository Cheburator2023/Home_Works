package ru.otus.processor;

import ru.otus.exception.TwoSecondsException;
import ru.otus.model.Message;

import java.time.LocalDateTime;
import java.util.function.Supplier;

public class ProcessorTwoSecondsException implements Processor {

    private final Supplier<LocalDateTime> localDateTimeSupplier;

    public ProcessorTwoSecondsException(Supplier<LocalDateTime> localDateTimeSupplier) {
        this.localDateTimeSupplier = localDateTimeSupplier;
    }

    @Override
    public Message process(Message message) {
        if (localDateTimeSupplier.get().getSecond() % 2 == 0) {
            throw new TwoSecondsException("Two seconds exception");
        }
        return message;
    }
}
