package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected void insertElement(Resume r, int index) {
        storage[size] = r;
    }

    @Override
    protected void fillDeletedElement(int index) {
        System.arraycopy(storage, index + 1, storage, index, size - index - 1);
    }

    @Override
    protected int findIndex(String uuidString) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuidString)) {
                System.out.println("index " + i);            // delete
                return i;
            }
        }
        return -1;
    }
}
