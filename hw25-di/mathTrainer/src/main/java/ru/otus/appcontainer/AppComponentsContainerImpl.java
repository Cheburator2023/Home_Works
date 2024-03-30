package ru.otus.appcontainer;

import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import java.lang.reflect.Method;
import java.util.*;

@SuppressWarnings("squid:S1068")
public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);
        Object configInstance = createConfigInstance(configClass);

        List<Method> componentMethods = Arrays.stream(configClass.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(AppComponent.class))
                .sorted(Comparator.comparingInt(method -> method.getAnnotation(AppComponent.class).order()))
                .toList();

        for (Method method : componentMethods) {
            AppComponent appComponent = method.getAnnotation(AppComponent.class);
            Object component = createComponent(configInstance, method);
            appComponents.add(component);
            appComponentsByName.put(appComponent.name(), component);
        }
    }

    private Object createConfigInstance(Class<?> configClass) {
        try {
            return configClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create an instance of the config class", e);
        }
    }

    private Object createComponent(Object configInstance, Method method) {
        try {
            Class<?>[] parameterTypes = method.getParameterTypes();
            Object[] parameters = new Object[parameterTypes.length];

            for (int i = 0; i < parameterTypes.length; i++) {
                parameters[i] = getAppComponent(parameterTypes[i]);
                if (parameters[i] == null) {
                    throw new RuntimeException("Cannot find component for type " + parameterTypes[i].getName());
                }
            }

            return method.invoke(configInstance, parameters);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create a component", e);
        }
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        for (Object component : appComponents) {
            if (componentClass.isAssignableFrom(component.getClass())) {
                return (C) component;
            }
        }
        throw new RuntimeException("Component not found for: " + componentClass.getName());
    }

    @SuppressWarnings("unchecked")
    @Override
    public <C> C getAppComponent(String componentName) {
        Object component = appComponentsByName.get(componentName);
        if (component == null) {
            throw new RuntimeException("Component not found for: " + componentName);
        }
        return (C) component;
    }
}
