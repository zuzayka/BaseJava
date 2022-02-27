package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage implements Storage {
    private static final int STORAGE_LIMIT = 10000;
    private int size = 0;

    private Resume[] storage = new Resume[STORAGE_LIMIT];

    public int getSize() {
        return size;
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume r) {
        int index = findIndex(r.getUuid());
        if (index >= 0) {
            storage[index] = r;
            return;
        }
        System.out.println("Error: " + r.getUuid() + " is not updated");
    }

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
        storage[size] = r;
        size++;
    }

    public Resume get(String uuid) {
        if (findIndex(uuid) >= 0) {
            return storage[findIndex(uuid)];
        }
        System.out.println("Error: " + uuid + " is not got");
        return null;
    }

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

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int findIndex(String uuidString) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuidString)) {
                System.out.println("index " + i);            // delete
                return i;
            }
        }
        return -1;
    }
}
