package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import lombok.SneakyThrows;
import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.DataTemplateException;
import ru.otus.core.repository.executor.DbExecutor;

/** Сохратяет объект в базу, читает объект из базы */
@SuppressWarnings("java:S1068")
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;

    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
    }

    @SneakyThrows
    public Optional<T> findById(Connection connection, long id) {
        String sql = entitySQLMetaData.getSelectByIdSql();
        List<Object> params = new ArrayList<>();
        params.add(id);
        try {
            return dbExecutor.executeSelect(connection, sql, params, resultSet -> {
                try {
                    return mapResultSetToObject(resultSet);
                } catch (SQLException e) {
                    throw new DataTemplateException(e);
                }
            });
        } catch (DataTemplateException e) {
            throw new DataTemplateException("Operation findById failure: ", e);
        }
    }

    @SneakyThrows
    @Override
    public List<T> findAll(Connection connection) {
        String sql = entitySQLMetaData.getSelectAllSql();
        List<Object> params = new ArrayList<>();
        try {
            Optional<List<T>> ts = dbExecutor.executeSelect(connection, sql, params, resultSet -> {
                try {
                    return mapResultSetToObjectList(resultSet);
                } catch (SQLException e) {
                    throw new DataTemplateException(e);
                }
            });
            return ts.orElse(Collections.emptyList());
        } catch (DataTemplateException e) {
            throw new DataTemplateException("Operation findAll failure: ", e);
        }
    }


    @SneakyThrows
    @Override
    public long insert(Connection connection, T object) {
        String sql = entitySQLMetaData.getInsertSql();
        List<Object> params = mapObjectToParams(object);
        return dbExecutor.executeStatement(connection, sql, params);
    }

    @SneakyThrows
    @Override
    public void update(Connection connection, T object) {
        String sql = entitySQLMetaData.getUpdateSql();
        List<Object> params = mapObjectToParams(object);
        dbExecutor.executeStatement(connection, sql, params);
    }

    @SneakyThrows
    private T mapResultSetToObject(ResultSet resultSet) throws SQLException {
        T object;
        try {
            var entityClassMetaData = entitySQLMetaData.getEntityClassMetaData();
            object = (T) entityClassMetaData.getConstructor().newInstance();
            for (Field field : entityClassMetaData.getAllFields()) {
                field.setAccessible(true);
                field.set(object, resultSet.getObject(field.getName()));
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | SQLException e) {
            throw new DataTemplateException("Failed to map ResultSet to object", e);
        }
        return object;
    }

    private List<T> mapResultSetToObjectList(ResultSet resultSet) throws SQLException {
        List<T> objects = new ArrayList<>();
        while (resultSet.next()) {
            objects.add(mapResultSetToObject(resultSet));
        }
        return objects;
    }

    private List<Object> mapObjectToParams(T object) {
        List<Object> params = new ArrayList<>();
        var entityClassMetaData = entitySQLMetaData.getEntityClassMetaData();
        for (Field field : entityClassMetaData.getFieldsWithoutId()) {
            field.setAccessible(true);
            try {
                params.add(field.get(object));
            } catch (IllegalAccessException | DataTemplateException e) {
                throw new DataTemplateException("Failed to map object to params", e);
            }
        }
        return params;
    }
}
