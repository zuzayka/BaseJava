package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.ConnectionFactory;
import com.urise.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class SqlStorage implements Storage {
    private static final String CLEAR_ALL_RESUMES = "DELETE FROM resume";
    private static final String SAVE_RESUME = "INSERT INTO resume (uuid, full_name) VALUES(?, ?)";
    private static final String GET_RESUME = "SELECT * FROM resume res WHERE res.uuid = ?";
    private static final String SIZE_RESUME = "SELECT COUNT(*) FROM resume";
    private static final String UPDATE_RESUME = "UPDATE resume res SET uuid = ?, full_name = ? WHERE res.uuid = ?";
    private static final String DELETE_RESUME = "DELETE FROM resume res WHERE res.uuid = ?";
    private static final String GET_ALL_RESUME = "SELECT * FROM resume";
    public final ConnectionFactory connectionFactory;
    private final SqlHelper sqlHelper; // Idea предлагает убрать этот SqlHelper
    private Resume r;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        this.connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        this.sqlHelper = new SqlHelper(connectionFactory);
    }

    @Override
    public int getSize() {
        AtomicInteger size = new AtomicInteger();
        new SqlHelper(connectionFactory).execute(connectionFactory, SIZE_RESUME, ps -> {
            ResultSet rs = ps.executeQuery();
            try {
                rs.next();
                size.set(rs.getInt(1));
            } catch (SQLException e) {
                throw new StorageException(e);
            }
        });
        return size.get();
    }

    @Override
    public void clear() {
        new SqlHelper(connectionFactory).execute(connectionFactory, CLEAR_ALL_RESUMES, (PreparedStatement::execute));
    }

    @Override
    public void update(Resume r) {
        getResume(r.getUuid());
        new SqlHelper(connectionFactory).execute(connectionFactory, UPDATE_RESUME, (ps -> {
            ps.setString(1, r.getUuid());
            ps.setString(2, r.getFullName());
            ps.setString(3, r.getUuid());
            ps.execute();
        }));
    }

    @Override
    public void save(Resume r) {
        new SqlHelper(connectionFactory).execute(connectionFactory, SAVE_RESUME, (ps -> {
            ps.setString(1, r.getUuid());
            ps.setString(2, r.getFullName());
            ps.execute();
        }));
    }

    @Override
    public Resume getResume(String uuid) {
        new SqlHelper(connectionFactory).execute(connectionFactory, GET_RESUME, (ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            r = new Resume(uuid, rs.getString("full_name"));

        }));
        return r;
    }

    @Override
    public void delete(String uuid) {
        getResume(uuid);
        new SqlHelper(connectionFactory).execute(connectionFactory, DELETE_RESUME, (ps -> {
            ps.setString(1, uuid);
            ps.execute();
        }));
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> list = new ArrayList<>();
        new SqlHelper(connectionFactory).execute(connectionFactory, GET_ALL_RESUME, (ps -> {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String uuid = rs.getString("uuid");
                String fullName = rs.getString("full_name");
                list.add(new Resume(uuid, fullName));
            }
            list.sort(Comparator.comparing(Resume::getUuid));
        }));
        return list;
    }
}
