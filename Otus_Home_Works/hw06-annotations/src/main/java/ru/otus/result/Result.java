package ru.otus.result;

import lombok.Data;

import java.util.Map;
import java.util.Optional;

@Data
public class Result {

    @FunctionalInterface
    interface I {
        String generate();
        default void print(String value) {
            System.out.printf(Optional.ofNullable(value).orElseGet(this::generate));
        }
    }

    public void results(Map<String, String> map) {
        Integer tests = Optional.ofNullable(map.get("Tests"))
                .map(Integer::parseInt)
                .orElse(0);
        Integer passed = Optional.ofNullable(map.get("Passed"))
                .map(Integer::parseInt)
                .orElse(0);
        String s = String.format("Tests : %d\nPassed: %d\nFailed: %d\n",
                tests, passed, tests - passed);
        ((I)()-> "Тесты отсутствуют").print(s);
    }
}
