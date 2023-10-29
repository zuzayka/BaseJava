package com.urise.webapp.sql;

import com.urise.webapp.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Consumer;

public class SqlHelper {
    public ConnectionFactory factory;
    public PreparedStatement ps;
    public String query;
    public ResultSet rs;

    public SqlHelper(ConnectionFactory factory, String query) {
        this.factory = factory;
        this.query = query;
    }

    public void getExecute(Consumer<Connection> consumer) {
        try (Connection conn = factory.getConnection()) {
            ps = conn.prepareStatement(query);
            consumer.accept(conn);
            ps.execute();
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }


    public void getExecuteQuery(Consumer<Connection> consumer) {
        try (Connection conn = factory.getConnection()) {
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            consumer.accept(conn);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

}
