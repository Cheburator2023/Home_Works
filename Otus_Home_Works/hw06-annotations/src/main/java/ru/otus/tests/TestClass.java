package ru.otus.tests;

import lombok.SneakyThrows;
import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class TestClass {

    private Object instance;
    private final Class<?> clazz;

    public TestClass(Class<?> clazz) {
        this.clazz = clazz;
    }

    @SneakyThrows
    public Map<String, String> invokeMethods() {
        Map<String, String> map = new HashMap<>();

        for (Method method : clazz.getDeclaredMethods()) {
             if (method.isAnnotationPresent(Test.class)) {
                try {
                    methodInvokeBeforeAndAfter(Before.class);

                    method.invoke(instance());

                    methodInvokeBeforeAndAfter(After.class);
                    if (!map.containsKey("Passed")) {
                        map.put("Passed", String.valueOf(1));
                    } else {
                        map.replace("Passed", String.valueOf(Integer.parseInt(map.get("Passed")) + 1));
                    }
                } catch (InvocationTargetException wrappedExc) {
                    Throwable exc = wrappedExc.getCause();
                    System.out.println(method + " failed: " + exc);
                }
                if (!map.containsKey("Tests")) {
                    map.put("Tests", String.valueOf(1));
                } else {
                    map.replace("Tests", String.valueOf(Integer.parseInt(map.get("Tests")) + 1));
                }
            }
        }

        return map;
    }

    @SneakyThrows
    private void methodInvokeBeforeAndAfter(Class<? extends Annotation> annotation) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(annotation)) {
                try {
                    method.invoke(instance());
                } catch (InvocationTargetException wrappedExc) {
                    Throwable exc = wrappedExc.getCause();
                    System.out.println(method.getName() + " failed: " + exc);
                }
            }
        }
    }


    @SneakyThrows
    public Object instance(Object... args) {
        if (instance == null) {
            instance = clazz.getConstructor().newInstance(args);
        }
        return instance;
    }
}
