package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage {
    protected Map<Resume, String> mapResumeStorage = new HashMap<>();

    @Override
    public int getSize() {
        return mapResumeStorage.size();
    }

    @Override
    public void clear() {
        mapResumeStorage.clear();
    }

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    protected boolean isExist(Object searchKey) {
        return mapResumeStorage.containsValue(searchKey);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return getKey(mapResumeStorage, (String) searchKey);
    }

    protected void doSave(Resume r, Object searchKey) {
        mapResumeStorage.put(r, (String) searchKey);
    }

    @Override
    protected void doDelete(Object searchKey) {
        mapResumeStorage.remove(getKey(mapResumeStorage, (String) searchKey));
    }

    @Override
    protected void doUpdate(Resume r, Object searchKey) {
        mapResumeStorage.put(r, (String) searchKey);
    }

    @Override
    public String toString() {
        return "mapResumeStorage=" + mapResumeStorage;
    }

    private Resume getKey(Map<Resume, String> map, String string) {
        for (Resume key : map.keySet()) {
            String keyString = key.getUuid();
            if (string.equals(keyString)) {

                return key;
            }
        }
        return null;
    }

    @Override
    protected List<Resume> doCopyAll() {
        return new ArrayList<>(mapResumeStorage.keySet());
    }
}
