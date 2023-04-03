package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    protected Map<String, Resume> storageMap = new HashMap<>();

    @Override
    public int getSize() {
        return storageMap.size();
    }

    @Override
    public void clear() {
        storageMap.clear();
    }

    @Override
    public Resume[] getAll() {
        return storageMap.values().toArray(new Resume[0]);
    }

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    protected boolean isExist(Object searchKey) {
        return storageMap.containsKey(searchKey);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return storageMap.get(searchKey);
    }

    protected void doSave(Resume r, Object searchKey) {
        storageMap.put((String) searchKey, r);
    }

    @Override
    protected void doDelete(Object searchKey) {
        storageMap.remove(searchKey);
    }

    @Override
    protected void doUpdate(Resume r, Object searchKey) {
        storageMap.put((String) searchKey, r);
    }

    @Override
    public String toString() {
        return "MapStorage{" + "storageMap=" + storageMap + '}';
    }
}
