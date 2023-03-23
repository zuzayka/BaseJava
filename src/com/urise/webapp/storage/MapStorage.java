package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    protected Map<Object, Resume> storageMap = new HashMap<>();

    @Override
    public void clear() {
        storageMap.clear();
        size = 0;
    }

    @Override
    public Resume[] getAll() {
        return storageMap.values().toArray(new Resume[0]);
    }

    @Override
    protected Object getSearchKey(String uuid) {
        if (size == 0) {
            return uuid;
        }
        for (Resume r : storageMap.values()) {
            String resumeUuid = r.getUuid();
            if (!uuid.equals(resumeUuid)) {
                return uuid;
            }
        }
        return "Key Exist";
    }

    protected boolean isExist(String uuid) {
        for (Resume r : storageMap.values()) {
            String resumeUuid = r.getUuid();
            if (uuid.equals(resumeUuid)) {
                return true;
            }
        }
        return false;
    }


    @Override
    protected Resume doGet(Object searchKey) {
        return storageMap.get(searchKey);
    }

    protected void doSave(Resume r, Object searchKey) {
        storageMap.put(searchKey, r);
        size++;
    }

    @Override
    protected void doDelete(Object searchKey) {
        storageMap.remove(searchKey);
        size--;
    }

    @Override
    protected void doUpdate(Resume r) {
        Object searchKey = getSearchKey(r.getUuid());
        storageMap.put(searchKey, r);
    }

    @Override
    public String toString() {
        return "MapStorage{" + "storageMap=" + storageMap + '}';
    }
}
