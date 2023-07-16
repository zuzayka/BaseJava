package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;

public class ObjectStreamPathStorage extends AbstractPathStorage implements ObjectStreamStorageInterface {


    protected ObjectStreamPathStorage(Path dir) {
        super(dir);
    }

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {

    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        return null;
    }
}
