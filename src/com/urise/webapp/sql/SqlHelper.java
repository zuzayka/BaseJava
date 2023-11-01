package com.urise.webapp.sql;

import com.urise.webapp.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    public ConnectionFactory factory;

    public SqlHelper(ConnectionFactory factory) {
        this.factory = factory;
    }

    public void execute(ConnectionFactory factory, String query, Executor executor) {
        try (Connection conn = factory.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(query);
            executor.execute(ps);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @FunctionalInterface
    public interface Executor {
        void execute(PreparedStatement ps) throws SQLException;
    }
}
