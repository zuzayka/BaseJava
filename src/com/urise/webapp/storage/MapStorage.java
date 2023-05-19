package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapStorage extends AbstractStorage<String> {
    protected Map<String, Resume> storageMap = new HashMap<>();

    @Override
    public int getSize() {
        return storageMap.size();
    }

    @Override
    public void clear() {
        storageMap.clear();
    }

//    @Override
//    public List<Resume> getAllSorted() {
//        List<Resume> sortedList = new ArrayList<>(storageMap.values());
//        sortedList.sort(RESUME_COMPARATOR);
//        return sortedList;
//    }

    @Override
    protected List<Resume> doCopyAll() {
        return new ArrayList<>(storageMap.values());
    }

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    protected boolean isExist(String searchKey) {
        return storageMap.containsKey(searchKey);
    }

    @Override
    protected Resume doGet(String searchKey) {
        return storageMap.get(searchKey);
    }

    @Override
    protected void doSave(Resume r, String searchKey) {
        storageMap.put(searchKey, r);
    }

    @Override
    protected void doDelete(String searchKey) {
        storageMap.remove(searchKey);
    }

    @Override
    protected void doUpdate(Resume r, String searchKey) {
        storageMap.put(searchKey, r);
    }

    @Override
    public String toString() {
        return "storageMap=" + storageMap;
    }
}
