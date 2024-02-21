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
@SuppressWarnings({"java:S1192","java:S106"})
public class TestClass {

    private final Class<?> clazz;

    public TestClass(Class<?> clazz) {
        this.clazz = clazz;
    }

    @SneakyThrows
    public Map<String, String> invokeMethods() {
        Map<String, String> map = new HashMap<>();

        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Test.class)) {
                Object instance = instance();
                try {
                    methodInvokeBefore(instance);

                    method.invoke(instance);

                    methodInvokeAfter(instance);
                    if (!map.containsKey("Passed")) {
                        map.put("Passed", String.valueOf(1));
                    } else {
                        map.replace("Passed", String.valueOf(Integer.parseInt(map.get("Passed")) + 1));
                    }
                } catch (InvocationTargetException wrappedExc) {
                    Throwable exc = wrappedExc.getCause();
                    System.out.println(method.getName() + " failed: " + exc);
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
    private void methodInvokeBefore(Object instance) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Before.class)) {
                try {
                    method.invoke(instance);
                } catch (InvocationTargetException wrappedExc) {
                    Throwable exc = wrappedExc.getCause();
                    System.out.println(method.getName() + " failed: " + exc);
                }
            }
        }
    }

    @SneakyThrows
    private void methodInvokeAfter(Object instance) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(After.class)) {
                try {
                    method.invoke(instance);
                } catch (InvocationTargetException wrappedExc) {
                    Throwable exc = wrappedExc.getCause();
                    System.out.println(method.getName() + " failed: " + exc);
                }
            }
        }
    }


    @SneakyThrows
    public Object instance() {
        return clazz.getConstructor().newInstance();
    }
}
