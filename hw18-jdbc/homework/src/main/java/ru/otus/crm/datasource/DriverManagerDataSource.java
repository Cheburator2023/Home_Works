package ru.otus.crm.datasource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ru.otus.core.sessionmanager.DataBaseOperationException;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

public class DriverManagerDataSource implements DataSource {
    private DataSource dataSourcePool;

    public DriverManagerDataSource(String url, String user, String pwd) {
        createConnectionPool(url, user, pwd);
    }

    @Override
    public Connection getConnection() throws SQLException {
        try {
            return dataSourcePool.getConnection();
        } catch (SQLException e) {
            throw new DataBaseOperationException("Failed to get connection from data source", e);
        }
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        try {
            return dataSourcePool.getLogWriter();
        } catch (SQLException e) {
            throw new DataBaseOperationException("Failed to get log writer from data source", e);
        }
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        try {
            dataSourcePool.setLogWriter(out);
        } catch (SQLException e) {
            throw new DataBaseOperationException("Failed to set log writer for data source", e);
        }
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        try {
            return dataSourcePool.getLoginTimeout();
        } catch (SQLException e) {
            throw new DataBaseOperationException("Failed to get login timeout from data source", e);
        }
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        try {
            dataSourcePool.setLoginTimeout(seconds);
        } catch (SQLException e) {
            throw new DataBaseOperationException("Failed to set login timeout for data source", e);
        }
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        try {
            return dataSourcePool.unwrap(iface);
        } catch (SQLException e) {
            throw new DataBaseOperationException("Failed to unwrap data source", e);
        }
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        try {
            return dataSourcePool.isWrapperFor(iface);
        } catch (SQLException e) {
            throw new DataBaseOperationException("Failed to check if data source is wrapper for " + iface.getName(), e);
        }
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