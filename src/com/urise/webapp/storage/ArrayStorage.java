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
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(r.getUuid())) {
                storage[i] = r;
                return;
            }
        }
        System.out.println("Error in update(Resume r) method: Resume with uuid = " + r.getUuid() + " not in the Resumes");
    }

    public void save(Resume r) {
        if (size == 9999) {
            System.out.println("Error in save(Resume r) method: Resumes is full");
            return;
        }
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(r.getUuid())) {
                System.out.println("Error in save(Resume r) method: Resume with uuid = " + r.getUuid() + " exist in the Resumes");
                return;
            }
        }
        storage[size] = r;
        size++;
    }

    public Resume get(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return storage[i];
            }
        }
        System.out.println("Error in get(String uuid) method: Resume with uuid = " + uuid + " not in the Resumes");
        return null;
    }

    public void delete(String uuid) {
        if (uuid.equals(storage[size - 1].getUuid())) {
            storage[size - 1] = null;
            size--;
        }
        for (int i = 0; i < size - 1; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                System.arraycopy(storage, i + 1, storage, i, size - i - 1);
                storage[size - 1] = null;
                size--;
                return;
            }
        }
        System.out.println("Error in delete(String uuid) method: Resume with uuid = " + uuid + " not in the Resumes");
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }
}
