package com.urise.webapp.sql;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.StorageException;

import java.sql.SQLException;

public class ExceptionUtil {
    private ExceptionUtil() {
    }

    public static StorageException convertException(SQLException e) {
        if (e.getSQLState().equals("32505")) {
            return new ExistStorageException("Resume already exist");
        }
        return new StorageException(e);
    }
}
