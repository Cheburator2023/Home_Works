package ru.otus.jdbc.mapper;

import lombok.Getter;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;


public class EntitySQLMetaDataImpl implements EntitySQLMetaData {

    private final EntityClassMetaData<?> entityClassMetaData;

    public EntitySQLMetaDataImpl(EntityClassMetaData<?> entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public String getSelectAllSql() {
        String tableName = entityClassMetaData.getName();
        return "SELECT * FROM " + tableName;
    }

    @Override
    public String getSelectByIdSql() {
        String tableName = entityClassMetaData.getName();
        String idColumnName = entityClassMetaData.getIdField().getName();
        return "SELECT * FROM " + tableName + " WHERE " + idColumnName + " = ?";
    }

    @Override
    public String getInsertSql() {
        String tableName = entityClassMetaData.getName();
        List<Field> fieldsWithoutId = entityClassMetaData.getFieldsWithoutId();
        String columns = fieldsWithoutId.stream()
                .map(Field::getName)
                .collect(Collectors.joining(", "));
        String placeholders = fieldsWithoutId.stream()
                .map(field -> "?")
                .collect(Collectors.joining(", "));
        return "INSERT INTO " + tableName + " (" + columns + ") VALUES (" + placeholders + ")";
    }

    @Override
    public String getUpdateSql() {
        String tableName = entityClassMetaData.getName();
        String idColumnName = entityClassMetaData.getIdField().getName();
        List<Field> fieldsWithoutId = entityClassMetaData.getFieldsWithoutId();
        String setClause = fieldsWithoutId.stream()
                .map(field -> field.getName() + " = ?")
                .collect(Collectors.joining(", "));
        return "UPDATE " + tableName + " SET " + setClause + " WHERE " + idColumnName + " = ?";
    }

    public EntityClassMetaData<?> getEntityClassMetaData() {
        return entityClassMetaData;
    }
}
