package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface ObjectStreamStorageInterface {
    default void doWrite(Resume r, OutputStream os) throws IOException {

    }

    default Resume doRead(InputStream is) throws IOException {
        return null;
    }
}
