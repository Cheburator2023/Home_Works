package ru.otus.tests;

import lombok.SneakyThrows;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class TestClass {

    private final Logger logger = LoggerFactory.getLogger(TestClass.class);
    private Object instance;
    private final Class<?> clazz;

    public TestClass(Class<?> clazz) {
        this.clazz = clazz;
    }

    @SneakyThrows
    public Map<String, String> invokeMethods(Class<? extends Annotation> annotation) {
        Map<String, String> map = new HashMap<>();
        Method beforeMethod = null;
        Method afterMethod = null;

        for (Method m : clazz.getDeclaredMethods()) {
            if (m.isAnnotationPresent(Before.class)) {
                beforeMethod = m;
            } else if (m.isAnnotationPresent(After.class)) {
                afterMethod = m;
            } else if (m.isAnnotationPresent(Test.class)) {
                if (beforeMethod != null) {
                    beforeMethod.invoke(instance());
                }

                try {
                    m.invoke(instance());

                    if (!map.containsKey("Passed")) {
                        map.put("Passed", String.valueOf(1));
                    } else {
                        map.replace("Passed", String.valueOf(Integer.parseInt(map.get("Passed")) + 1));
                    }
                } catch (InvocationTargetException wrappedExc) {
                    Throwable exc = wrappedExc.getCause();
                    System.out.println(m + " failed: " + exc);
                }

                if (afterMethod != null) {
                    afterMethod.invoke(instance());
                }

                if (!map.containsKey("Tests")) {
                    map.put("Tests", String.valueOf(1));
                    logger.info(() -> "map put");
                } else {
                    map.replace("Tests", String.valueOf(Integer.parseInt(map.get("Tests")) + 1));
                }
            }
        }

        return map;
    }


    @SneakyThrows
    public Object instance(Object... args) {
        if (instance == null) {
            instance = clazz.getConstructor().newInstance(args);
        }
        return instance;
    }
}
