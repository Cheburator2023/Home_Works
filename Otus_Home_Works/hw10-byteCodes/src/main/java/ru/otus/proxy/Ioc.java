package ru.otus.proxy;

import java.lang.reflect.Proxy;

public class Ioc {

    private Ioc() {
    }


    @SuppressWarnings("unchecked")
    public static <T> T createTestLogging(Class<T> tClass, T targetChanges) {
        return (T) Proxy.newProxyInstance(
                tClass.getClassLoader(),
                new Class<?>[]{tClass},
                new LogInvocationHandler(targetChanges)
        );
    }

}
