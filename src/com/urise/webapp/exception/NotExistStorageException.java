package com.urise.webapp.exception;

public class NotExistStorageException extends StorageException {
    public NotExistStorageException(Object searchKey) {
        super("Resume " + searchKey + " not exist", searchKey);
    }
}
