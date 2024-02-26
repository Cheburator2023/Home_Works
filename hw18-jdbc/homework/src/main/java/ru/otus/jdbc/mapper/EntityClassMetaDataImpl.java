package ru.otus.jdbc.mapper;

import ru.otus.jdbc.mapper.annotation.Id;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {
    private final Class<T> clazz;
    private final Field idField;
    private final List<Field> allFields;
    private final List<Field> fieldsWithoutId;

    public EntityClassMetaDataImpl(Class<T> clazz) {
        this.clazz = clazz;
        this.idField = findIdField();
        this.allFields = findAllFields();
        this.fieldsWithoutId = findFieldsWithoutId();
    }

    @Override
    public String getName() {
        return clazz.getSimpleName();
    }

    @Override
    public Constructor<T> getConstructor() {
        try {
            return clazz.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Default constructor not found for class: " + clazz.getSimpleName(), e);
        }
    }

    @Override
    public Field getIdField() {
        return idField;
    }

    @Override
    public List<Field> getAllFields() {
        return allFields;
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return fieldsWithoutId;
    }

    private Field findIdField() {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Id.class)) {
                return field;
            }
        }
        throw new RuntimeException("Id field not found for class: " + clazz.getSimpleName());
    }

    private List<Field> findAllFields() {
        return new ArrayList<>(Arrays.asList(clazz.getDeclaredFields()));
    }

    private List<Field> findFieldsWithoutId() {
        List<Field> fieldsWithoutId = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (!field.isAnnotationPresent(Id.class)) {
                fieldsWithoutId.add(field);
            }
        }
        return fieldsWithoutId;
    }
}