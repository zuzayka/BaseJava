package com.urise.webapp.sql;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    public ConnectionFactory factory;

    public SqlHelper(ConnectionFactory factory) {
        this.factory = factory;
    }

    public void execute(String query, Executor executor) {
        try (Connection conn = factory.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(query);
            executor.execute(ps);
        } catch (SQLException e) {
            if (Integer.parseInt(e.getSQLState()) == 23505) {
                throw new ExistStorageException("");
            } else {
                throw new StorageException(e);
            }
        }
    }

    public <R> R executeWithReturn(String query, ExecutorWithReturn executor) {
        try (Connection conn = factory.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(query);
            return  (R) executor.executeWithReturn(ps);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    public <T> T transactionalExecute(SqlTransaction<T> executor) {
        try (Connection conn = factory.getConnection()) {
            try {
                conn.setAutoCommit(false);
                T res = executor.execute(conn);
                conn.commit();
                return res;
            } catch (SQLException e) {
                conn.rollback();
                throw ExceptionUtil.convertException(e);
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @FunctionalInterface
    public interface Executor {
        void execute(PreparedStatement ps) throws SQLException;
    }

    @FunctionalInterface
    public interface ExecutorWithReturn<R> {
        R executeWithReturn(PreparedStatement ps) throws SQLException;
    }
}
