package ru.otus.tests;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor(staticName = "set")
public class TestClass {

    private final Logger logger = LoggerFactory.getLogger(TestClass.class);
    private Object instance;
    private final Class<?> clazz;

    @SneakyThrows
    public Map<String, String> invokeMethods(Class<? extends Annotation> annotation) {
        Map<String, String> map = new HashMap<>();
        for (Method m : clazz.getDeclaredMethods()) {
            if (m.isAnnotationPresent(annotation)) {
                if (annotation.equals(Test.class)) {
                    if (!map.containsKey("Tests")) {
                        map.put("Tests", String.valueOf(1));
                        logger.info(() -> "map put");
                    } else {
                        map.replace("Tests", String.valueOf(Integer.parseInt(map.get("Tests")) + 1));
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
                        if (!map.containsKey("Failed " + m)) {
                            map.put("Failed" + m, m + " failed " + exc);
                        } else {
                            map.replace("Failed", m + " failed " + exc);
                        }
                        System.out.println(m + " failed: " + exc);
                    }
                } else if (annotation.equals(Before.class) || annotation.equals(After.class)) {
                    m.invoke(instance());
                }
            }
        }
        return map;
    }


    @SneakyThrows
    public Object instance(Object... args) {
        if (instance == null) {
            instance = getConstructor().newInstance(args);
        }
        return instance;
    }

    @NonNull
    @SneakyThrows
    private Constructor<?> getConstructor() {
        return clazz.getConstructor();
    }
}
