package com.urise.webapp.sql;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    public ConnectionFactory factory;
    public Resume resume;
    public int size;

    public SqlHelper(ConnectionFactory factory) {
        this.factory = factory;
    }

    public void execute(String query, Executor executor) {
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
