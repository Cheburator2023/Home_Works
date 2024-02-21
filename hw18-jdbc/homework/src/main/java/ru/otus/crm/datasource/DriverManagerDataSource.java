package ru.otus.crm.datasource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.SneakyThrows;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

public class DriverManagerDataSource implements DataSource {
    private DataSource dataSourcePool;

    public DriverManagerDataSource(String url, String user, String pwd) {
        createConnectionPool(url, user, pwd);
    }

    @Override
    public Connection getConnection() throws SQLException {
        return dataSourcePool.getConnection();
    }

    @Override
    public Connection getConnection(String username, String password) {
        throw new UnsupportedOperationException();
    }

    @SneakyThrows
    @Override
    public PrintWriter getLogWriter() {
        return dataSourcePool.getLogWriter();
    }

    @SneakyThrows
    @Override
    public void setLogWriter(PrintWriter out) {
        dataSourcePool.setLogWriter(out);
    }

    @SneakyThrows
    @Override
    public int getLoginTimeout() {
        return dataSourcePool.getLoginTimeout();
    }

    @SneakyThrows
    @Override
    public void setLoginTimeout(int seconds) {
        dataSourcePool.setLoginTimeout(seconds);
    }

    @Override
    public Logger getParentLogger() {
        throw new UnsupportedOperationException();
    }

    @SneakyThrows
    @Override
    public <T> T unwrap(Class<T> iface) {
        return dataSourcePool.unwrap(iface);
    }

    @SneakyThrows
    @Override
    public boolean isWrapperFor(Class<?> iface) {
        return dataSourcePool.isWrapperFor(iface);
    }

    private void createConnectionPool(String url, String user, String pwd) {
        var config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setConnectionTimeout(3000); // ms
        config.setIdleTimeout(60000); // ms
        config.setMaxLifetime(600000); // ms
        config.setAutoCommit(false);
        config.setMinimumIdle(5);
        config.setMaximumPoolSize(10);
        config.setPoolName("DemoHiPool");
        config.setRegisterMbeans(true);

        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        config.setUsername(user);
        config.setPassword(pwd);

        dataSourcePool = new HikariDataSource(config);
    }
}
