package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    @Override
    public String toString() {
        return "AbstractArrayStorage{" + "storage=" + Arrays.toString(storage) + '}';
    }

    protected abstract void insertElement(Resume r, int index);

    protected abstract void fillDeletedElement(int index);

    protected boolean isExist(String uuid) {
        return (int) getSearchKey(uuid) >= 0;
    }

    @Override
    protected Resume doGet(Object searchKey) {
        int index = (int) searchKey;
        return storage[index];
    }

    protected void doSave(Resume r, Object searchKey) {
        int index = (int) searchKey;
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", r.getUuid());
        } else {
            insertElement(r, index);
            size++;
        }
    }

    @Override
    protected void doDelete(Object searchKey) {
        int index = (int) searchKey;
        fillDeletedElement(index);
        storage[size - 1] = null;
        size--;
    }

    protected void doUpdate(Resume r){
        int index = (int) getSearchKey(r.getUuid());
            storage[index] = r;
    }
}