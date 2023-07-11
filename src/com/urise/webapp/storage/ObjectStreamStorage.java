package com.urise.webapp.storage;

import com.urise.webapp.ResumeTestData;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.*;

public class ObjectStreamStorage extends AbstractFileStorage {
    protected ObjectStreamStorage(File directory) {
        super(directory);
    }

    @Override
    protected void doWrite(Resume r, OutputStream os) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(os)) {
            oos.writeObject(r);
        }
    }

    @Override
    protected Resume doRead(InputStream is) throws IOException {
        try (ObjectInputStream ois = new ObjectInputStream(is)) {
            return (Resume) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new StorageException("Error read resume", null, e);
        }
    }

    public static void main(String[] args) {
        final File STORAGE_DIR = new File("/home/miux/Java/basejava/storage");
        Storage storage = new ObjectStreamStorage(STORAGE_DIR);
        final String UUID_1 = "uuid1";
        final String UUID_2 = "uuid2";
        final String UUID_3 = "uuid3";
        final String UUID_4 = "uuid4";
        final String FULL_NAME_1 = "Petrov Petr";
        final String FULL_NAME_2 = "Ivanov Ivan";
        final String FULL_NAME_3 = "Saidova Zuleykha";
        final String FULL_NAME_4 = "Petrov Petr";
        final Resume R1 = ResumeTestData.resumeFill(UUID_1, FULL_NAME_1);
        final Resume R2 = ResumeTestData.resumeFill(UUID_2, FULL_NAME_2);
        final Resume R3 = ResumeTestData.resumeFill(UUID_3, FULL_NAME_3);
        final Resume R4 = ResumeTestData.resumeFill(UUID_4, FULL_NAME_4);
        storage.clear();
        storage.save(R2);
        storage.save(R1);
        storage.save(R3);
    }
}