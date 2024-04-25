package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.*;
import com.urise.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM contact" + " WHERE resume_uuid = ?; ")) {
                ps.setString(1, r.getUuid());
                ps.execute();
            }
            insertContact(conn, r);

            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM section"
                                                              + " WHERE resume_uuid = ?; ")) {
                ps.setString(1, r.getUuid());
                ps.execute();
            }
            insertSection(conn, r);
            return null;
        });
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
            insertSection(conn, r);
            return null;
        });
    }

    @Override
    public Resume getResume(String uuid) {
        return sqlHelper.executeWithReturn("SELECT * FROM resume r "
                                           + " LEFT JOIN contact c "
                                           + " ON r.uuid = c.resume_uuid "
                                           + " LEFT JOIN section s"
                                           + " ON r.uuid = s.resume_uuid"
                                           + " WHERE uuid = ?"

                , ps -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume r = new Resume(uuid, rs.getString("full_name"));
                    do {
                        addContact(r, rs);
                        addSection(r, rs);
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

    public List<Resume> getAllSorted() {
        List<Resume> resumes = new ArrayList<>();
        sqlHelper.execute("""
        SELECT * FROM resume
        ORDER BY full_name
        """, ps -> {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String uuid = rs.getString("uuid");
                Resume resume = new Resume(uuid, rs.getString("full_name"));
                resumes.add(resume);
            }
        });

        sqlHelper.execute("""
        SELECT * FROM contact
        """, ps -> {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String uuid = rs.getString("resume_uuid");
                Resume resume = resumes.stream()
                        .filter(r -> r.getUuid().equals(uuid))
                        .findFirst()
                        .orElse(null);
                if (resume != null) {
                    addContact(resume, rs);
                }
            }
        });

        sqlHelper.execute("""
        SELECT * FROM section
        """, ps -> {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String uuid = rs.getString("resume_uuid");
                Resume resume = resumes.stream()
                        .filter(r -> r.getUuid().equals(uuid))
                        .findFirst()
                        .orElse(null);
                if (resume != null) {
                    addSection(resume, rs);
                }
            }
        });
        return resumes;
    }

    private void addContact(Resume resume, ResultSet resultSet) throws SQLException {
        String contactType = resultSet.getString("type");
        if (contactType != null) {
            resume.addContact(ContactType.valueOf(contactType), resultSet.getString("value"));
        }
    }

    private void addSection(Resume resume, ResultSet resultSet) throws SQLException {
        String typeString = resultSet.getString("section_type");
        if (Objects.equals(typeString, "PERSONAL") || Objects.equals(typeString, "OBJECTIVE") || Objects.equals(typeString, "ACHIEVEMENT") || Objects.equals(typeString, "QUALIFICATIONS")) {
            SectionType sectionType = SectionType.valueOf(typeString);
            switch (sectionType) {
                case PERSONAL, OBJECTIVE -> resume.addSection(sectionType, new TextSection(resultSet.getString("section_value")));
                case ACHIEVEMENT, QUALIFICATIONS -> {
                    resume.addSection(sectionType, new ListSection(List.of(resultSet.getString("section_value").split("\n"))));
                }
            }
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

    private void insertSection(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO section (resume_uuid, section_type, section_value) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, AbstractSection> e : r.getSectionType().entrySet()) {
                ps.setString(1, r.getUuid());
                SectionType keyType = e.getKey();
                ps.setString(2, e.getKey().name());
                switch (keyType) {
                    case PERSONAL, OBJECTIVE -> ps.setString(3, "ListSection{ list=" + e.getValue() + "\n");
                    case ACHIEVEMENT, QUALIFICATIONS -> ps.setString(3, String.join("\n", ((ListSection) e.getValue()).getList()));
                }
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }
}
