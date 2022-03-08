package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public abstract class AbstractArrayStorage implements  Storage{
    protected static final int STORAGE_LIMIT = 10000;
    protected int size = 0;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];

    public int getSize() {
        return size;
    }

    public Resume get(String uuid) {
        if (findIndex(uuid) >= 0) {
            return storage[findIndex(uuid)];
        }
        System.out.println("Error: " + uuid + " is not got");
        return null;
    }

    protected abstract int findIndex(String uuid);

}
