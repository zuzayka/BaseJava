package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.*;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10;  //  temp
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

//    @Override
//    public List<Resume> getAllSorted() {
//        List<Resume> sortedResume = new ArrayList<>(List.of(Arrays.copyOf(storage, size)));
//        sortedResume.sort(RESUME_COMPARATOR);
//        return sortedResume;
//    }

    @Override
    public String toString() {
        return "AbstractArrayStorage{" + "storage=" + Arrays.toString(storage) + '}';
    }

    protected abstract void insertElement(Resume r, int index);

    protected abstract void fillDeletedElement(int index);

    protected boolean isExist(Object searchKey) {
        return (int) searchKey >= 0;
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

    protected void doUpdate(Resume r, Object searchKey) {
        storage[(int) searchKey] = r;
    }

    @Override
    protected List<Resume> doCopyAll() {
        return List.of(Arrays.copyOf(storage, size));
    }
}