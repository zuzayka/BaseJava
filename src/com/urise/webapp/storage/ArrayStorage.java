package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private int size = 0;

    private Resume[] storage = new Resume[10000];

    public int getSize() {
        return size;
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume r) {
        if (getUuidNum(r.getUuid()) >= 0) {
            storage[getUuidNum(r.getUuid())] = r;
            return;
        }
        System.out.println("Error: " + r.getUuid() + " is not updated");
    }

    public void save(Resume r) {
        if (size == storage.length - 1) {
            System.out.println("Error: " + r.getUuid() + " is not saved");
            return;
        }
        if (getUuidNum(r.getUuid()) >= 0) {
            System.out.println("Error: " + r.getUuid() + " is not saved");
            return;
        }
        storage[size] = r;
        size++;
    }

    public Resume get(String uuid) {
        if (getUuidNum(uuid) >= 0) {
            return storage[getUuidNum(uuid)];
        }
        System.out.println("Error: " + uuid + " is not got");
        return null;
    }

    public void delete(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[size - 1].getUuid())) {
                if (i != size - 1) {
                    System.arraycopy(storage, i + 1, storage, i, size - i - 1);
                }
                storage[size - 1] = null;
                size--;
                return;
            }
        }
        System.out.println("Error: " + uuid + " is not deleted");
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    private int getUuidNum(String uuidCheck) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuidCheck)) {
                return i;
            }
        }
        return -1;
    }
}
