package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    protected void insertElement(Resume r, int index) {
        int insertIdx = -index - 1;
        System.arraycopy(storage, insertIdx, storage, insertIdx + 1, size - insertIdx);
    }

    @Override
    protected void fillDeletedElement(int index) {
        System.arraycopy(storage, index + 1, storage, index, size - index - 1);
    }

  /*
    @Override
    public void save(Resume r) {
        String uuid = r.getUuid();
        if (size == STORAGE_LIMIT) {
            System.out.println("Error: " + uuid + " is not saved");
            return;
        }
        int index = findIndex(uuid);
        if (index >= 0) {
            System.out.println("Error: " + uuid + " is not saved");
            return;
        }
        index = -index - 1;
        System.arraycopy(storage, index, storage, index + 1, size - index);
        storage[index] = r;
        size++;
    }

    @Override
    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            if (index < size - 1) {
                System.arraycopy(storage, index + 1, storage, index, size - index - 1);
            }
            storage[size - 1] = null;
            size--;
            return;
        }
        System.out.println("Error: " + uuid + " is not deleted");
    }
    */

    @Override
    protected int findIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}
