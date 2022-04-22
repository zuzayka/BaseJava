package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.LinkedList;

public abstract class AbstractStorage extends LinkedList implements Storage {
    protected LinkedList<Resume> storage = new LinkedList<>();

    @Override
    public int getSize() {
        return storage.size();
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public void update(Resume r) {
        int index = findIndex(r.getUuid());
        if (index >= 0) {
            storage.set(index, r);
            return;
        }
        throw new NotExistStorageException(r.getUuid());
    }

    @Override
    public void save(Resume r) {
        int index = findIndex(r.getUuid());
        if (index >= 0) {
            throw new ExistStorageException(r.getUuid());
        } else {
            storage.add(r);
        }
    }

    @Override
    public Resume getResume(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            return storage.get(index);
        }
        throw new NotExistStorageException(uuid);
    }

    @Override
    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            storage.remove(index);
            return;
        }
        throw new NotExistStorageException(uuid);
    }

    @Override
    public Resume[] getAll() {
        Resume[] resumes = new Resume[getSize()];
        return storage.toArray(resumes);
    }

    protected abstract int findIndex(String uuid);
}
