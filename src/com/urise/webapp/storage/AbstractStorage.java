package com.urise.webapp.storage;

public abstract class AbstractStorage implements Storage{
    protected int size = 0;

    @Override
    public int getSize() {
        return size;
    }

    protected abstract int findIndex(String uuid);
}
