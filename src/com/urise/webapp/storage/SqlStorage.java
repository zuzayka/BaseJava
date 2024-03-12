package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.*;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        this.sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public int getSize() {
        return sqlHelper.executeWithReturn("SELECT COUNT(*) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        });
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume", (PreparedStatement::execute));
    }

    @Override
    public void update(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
                    try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
                        ps.setString(2, r.getUuid());
                        ps.setString(1, r.getFullName());
                        if (ps.executeUpdate() == 0) {
                            throw new NotExistStorageException(r.getUuid());
                        }
                    }
                    try (PreparedStatement ps = conn.prepareStatement("DELETE FROM contact" +
                                                                      " WHERE resume_uuid = ?; " )) {
                        ps.setString(1, r.getUuid());
                        ps.execute();
                    }
                    insertContact(conn, r);
                    return null;
                }
        );
    }

    @Override
    public void save(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
                    try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                        ps.setString(1, r.getUuid());
                        ps.setString(2, r.getFullName());
                        ps.execute();
                    }

                    insertContact(conn, r);
                    return null;
                }
        );
    }

    @Override
    public Resume getResume(String uuid) {
        return sqlHelper.executeWithReturn("SELECT * FROM resume r " +
                        " LEFT JOIN contact c " +
                        "ON r.uuid = c.resume_uuid " +
                        "WHERE uuid = ?"

                , ps -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume r = new Resume(uuid, rs.getString("full_name"));
                    do {
                        addContact(r, rs);
                    } while (rs.next());
                    return r;
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute("DELETE FROM resume WHERE uuid = ?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.executeWithReturn("""
            SELECT * FROM resume r 
            LEFT JOIN contact c 
            ON r.uuid = c.resume_uuid
            ORDER BY r.full_name
            """, ps -> {
            final Map<String, Resume> resumeMap = new LinkedHashMap<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String uuid = rs.getString("uuid");
                Resume resume;
                if (resumeMap.containsKey(uuid)) {
                    resume = resumeMap.get(uuid);
                } else {
                    resume = new Resume(uuid, rs.getString(("full_name")));
                    resumeMap.put(uuid, resume);
                }
                addContact(resume, rs);
            }
            return new ArrayList<>(resumeMap.values());
        });
    }

    private  void addContact(Resume resume, ResultSet resultSet) throws SQLException {
        String contactType = resultSet.getString("type");
        if (contactType != null) {
            resume.addContact(ContactType.valueOf(contactType), resultSet.getString("value"));
        }
    }

    private void insertContact(Connection conn, Resume r) throws SQLException {
                    try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
                        for (Map.Entry<ContactType, String> e : r.getContactType().entrySet()) {
                            ps.setString(1, r.getUuid());
                            ps.setString(2, e.getKey().name());
                            ps.setString(3, e.getValue());
                            ps.addBatch();
                        }
                        ps.executeBatch();
                    }
    }
}
