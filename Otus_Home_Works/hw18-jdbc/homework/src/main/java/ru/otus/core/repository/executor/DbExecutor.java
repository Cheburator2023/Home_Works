package ru.otus.core.repository.executor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface DbExecutor {

    long executeStatement(Connection connection, String sql, List<Object> params) throws SQLException;

    <T> Optional<T> executeSelect(
            Connection connection, String sql, List<Object> params, Function<ResultSet, T> rsHandler) throws SQLException;
}
