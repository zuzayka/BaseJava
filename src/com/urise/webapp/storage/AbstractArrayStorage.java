package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;
    protected int size = 0;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public Resume get(String uuid) {
        if (findIndex(uuid) >= 0) {
            return storage[findIndex(uuid)];
        }
        System.out.println("Error: " + uuid + " is not got");
        return null;
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }


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
        insertElement(r, index);
        size++;
    }

    @Override
    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            fillDeletedElement(index);
            storage[size - 1] = null;
            size--;
            return;
        }
        System.out.println("Error: " + uuid + " is not deleted");
    }


    @Override
    public void update(Resume r) {
        int index = findIndex(r.getUuid());
        if (index >= 0) {
            storage[index] = r;
            return;
        }
        System.out.println("Error: " + r.getUuid() + " is not updated");
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Override
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    protected abstract void insertElement(Resume r, int index);

    protected abstract void fillDeletedElement(int index);

    protected abstract int findIndex(String uuid);
}
