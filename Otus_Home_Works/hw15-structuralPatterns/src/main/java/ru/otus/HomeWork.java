package ru.otus;

import ru.otus.handler.ComplexProcessor;
import ru.otus.listener.ListenerPrinterConsole;
import ru.otus.model.Message;
import ru.otus.model.ObjectForMessage;
import ru.otus.processor.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HomeWork {

    /*
    Реализовать to do:
      1. Добавить поля field11 - field13 (для field13 используйте класс ObjectForMessage)
      2. Сделать процессор, который поменяет местами значения field11 и field12
      3. Сделать процессор, который будет выбрасывать исключение в четную секунду (сделайте тест с гарантированным результатом)
            Секунда должна определяьться во время выполнения.
            Тест - важная часть задания
            Обязательно посмотрите пример к паттерну Мементо!
      4. Сделать Listener для ведения истории (подумайте, как сделать, чтобы сообщения не портились)
         Уже есть заготовка - класс HistoryListener, надо сделать его реализацию
         Для него уже есть тест, убедитесь, что тест проходит
    */

    public static void main(String[] args) {
        var processors = List.of(new ProcessorTwoSecondsException(LocalDateTime::now), new ProcessorFieldChanging());

        var complexProcessor = new ComplexProcessor(processors, ex -> {});
        var listenerPrinter = new ListenerPrinterConsole();
        complexProcessor.addListener(listenerPrinter);

        var field13 = new ObjectForMessage();
        var field13Data = new ArrayList<String>();
        field13Data.add("field13");
        field13.setData(field13Data);

        var firstMessage = new Message.Builder(1L)
                .field11("field11")
                .field12("field12")
                .field13(field13)
                .build();

        var firstResult = complexProcessor.handle(firstMessage);
        System.out.println("Result:" + firstResult);

        var secondMessage = new Message.Builder(2L)
                .field11("field11")
                .field12("field12")
                .field13(field13)
                .build();

        var secondResult = complexProcessor.handle(secondMessage);
        System.out.println("Result:" + secondResult);

        complexProcessor.removeListener(listenerPrinter);

        /*
          по аналогии с Demo.class
          из элеменов "to do" создать new ComplexProcessor и обработать сообщение
        */
    }
}
