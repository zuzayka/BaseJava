package com.urise.webapp.exception;

public class StorageException extends RuntimeException {
    private final String uuid;

    public StorageException( String message, String uuid) {
        super(message);
        this.uuid = message;
    }

    public StorageException(String message, Object searchKey) {
        super(message);
        this.uuid = (String) searchKey;
    }

    public StorageException(String message, String uuid, Exception e) {
        super(message, e);
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }
}
