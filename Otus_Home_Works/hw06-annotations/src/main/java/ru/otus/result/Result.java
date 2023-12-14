package ru.otus.result;

import lombok.Data;

import java.util.Map;
import java.util.Optional;

@Data
public class Result {

    public void results(Map<String, String> map) {
        Integer tests = Optional.ofNullable(map.get("Tests"))
                .map(Integer::parseInt)
                .orElse(0);
        Integer passed = Optional.ofNullable(map.get("Passed"))
                .map(Integer::parseInt)
                .orElse(0);
        System.out.printf("Tests : %d\nPassed: %d\nFailed: %d\n",
                tests, passed, tests - passed);
    }
}
