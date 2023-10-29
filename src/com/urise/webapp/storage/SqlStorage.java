package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SqlStorage implements Storage {
    private static final String CLEAR_ALL_RESUMES = "DELETE FROM resume";
    private static final String SAVE_RESUME = "INSERT INTO resume (uuid, full_name) VALUES(?, ?)";
    private static final String GET_RESUME = "SELECT * FROM resume res WHERE res.uuid = ?";
    private static final String SIZE_RESUME = "SELECT COUNT(*) FROM resume";
    private static final String UPDATE_RESUME = "UPDATE resume res SET uuid = ?, full_name = ? WHERE res.uuid = ?";
    private static final String DELETE_RESUME = "DELETE FROM resume res WHERE res.uuid = ?";
    private static final String GET_ALL_RESUME = "SELECT * FROM resume";
    public final ConnectionFactory connectionFactory;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        this.connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    public SqlStorage() {
        this.connectionFactory = () -> DriverManager.getConnection("jdbc:postgresql://localhost:5432/resumes", "postgres", "");
    }

//    public SqlStorage() {
//         this.connectionFactory = () -> DriverManager.getConnection("jdbc:postgresql://localhost:5432/resumes", "postgres", "");
//    }

        @Override
    public int getSize() {
        try (Connection conn = connectionFactory.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(SIZE_RESUME);
            ResultSet rs = ps.executeQuery();
            try {
                rs.next();
                return rs.getInt(1);
            } catch (SQLException e) {
                throw new StorageException(e);
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

//    @Override
//    public int getSize() {
//        AtomicInteger size = new AtomicInteger();
//        SqlHelper sqlHelper = new SqlHelper(connectionFactory, SIZE_RESUME);
//        sqlHelper.getExecuteQuery(consumer -> {
//            try {
//                sqlHelper.rs.next();
//                size.set(sqlHelper.rs.getInt(1));
//            } catch (SQLException e) {
//                throw new StorageException(e);
//            }
//        });
//        return size.get();
//    }

//    @Override
//    public void clear() {
//        SqlHelper sqlHelper = new SqlHelper(connectionFactory, CLEAR_ALL_RESUMES);
//        sqlHelper.getExecute(consumer -> {
//        });
//    }

    @Override
    public void clear() {
        try (Connection conn = connectionFactory.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(CLEAR_ALL_RESUMES);
            ps.execute();
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

//    @Override
//    public void update(Resume r) {
//        getResume(r.getUuid());
//        SqlHelper sqlHelper = new SqlHelper(connectionFactory, UPDATE_RESUME);
//        sqlHelper.getExecute(consumer -> {
//
//            sqlHelper.ps.setString(1, r.getUuid());
//            sqlHelper.ps.setString(2, r.getFullName());
//            sqlHelper.ps.setString(3, r.getUuid());
//        });
//    }

    @Override
    public void update(Resume r) {
        getResume(r.getUuid());
        try (Connection conn = connectionFactory.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(UPDATE_RESUME);
            ps.setString(1, r.getUuid());
            ps.setString(2, r.getFullName());
            ps.setString(3, r.getUuid());
            ps.execute();
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public void save(Resume r) {
        try (Connection conn = connectionFactory.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(SAVE_RESUME);
            ps.setString(1, r.getUuid());
            ps.setString(2, r.getFullName());
            ps.execute();
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public Resume getResume(String uuid) {
        try (Connection conn = connectionFactory.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(GET_RESUME);
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            Resume r = new Resume(uuid, rs.getString("full_name"));
            return r;
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public void delete(String uuid) {
        getResume(uuid);
        try (Connection conn = connectionFactory.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(DELETE_RESUME);
            ps.setString(1, uuid);
            ps.execute();
        } catch (SQLException e) {
            throw new StorageException(e);
        }

    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> list = new ArrayList<>();
        try (Connection conn = connectionFactory.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(GET_ALL_RESUME);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String uuid = rs.getString("uuid");
                String fullName = rs.getString("full_name");
                list.add(new Resume(uuid, fullName));
            }
            list.sort(Comparator.comparing(Resume::getUuid));
        } catch (SQLException e) {
            throw new StorageException(e);
        }
        return list;
    }

}
