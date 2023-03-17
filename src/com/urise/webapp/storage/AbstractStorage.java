package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {
    protected int size = 0;

    protected Object searchKey;
    protected final Integer NOT_EXIST_KEY = -1;

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public Resume getResume(String uuid) {
        searchKey = getExistingSearchKey(uuid);
        return doGetResume(searchKey);
    }

    @Override
    public void save(Resume r) {
        searchKey = getNotExistingSearchKey(r.getUuid());
        if ((int) searchKey  < 0) {
            doSave(r);
        }
    }

    @Override
    public void delete(String uuid) {
        searchKey = getExistingSearchKey(uuid);
        doDelete(doGetResume(searchKey));
    }

    @Override
    public void update(Resume r) {
        searchKey = getExistingSearchKey(r.getUuid());
        doUpdate(r);
    }

    private Object getExistingSearchKey(String uuid) {
        if (isExist(uuid)) {
            return getSearchKey(uuid);
        }
        throw new NotExistStorageException(uuid);
    }

    private Object getNotExistingSearchKey(String uuid) {
        if (!isExist(uuid)) {
            return getSearchKey(uuid);
        }
        throw new ExistStorageException(uuid);
    }

    protected abstract Resume doGetResume(Object searchKey);

    protected abstract void doSave(Resume r);

    protected abstract void doDelete(Resume r);

    protected abstract void doUpdate(Resume r);

    protected abstract Object getSearchKey(String uuid);

    protected abstract boolean isExist(String uuid);
}



