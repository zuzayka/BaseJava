package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapStorageResume extends AbstractStorage {
    protected Map<Resume, String> storageMapResume = new HashMap<>();

    @Override
    public int getSize() {
        return storageMapResume.size();
    }

    @Override
    public void clear() {
        storageMapResume.clear();
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> sortedList = new ArrayList<>(storageMapResume.keySet());
        sortedList.sort(RESUME_COMPARATOR);
        System.out.println(sortedList);  //  test
        return sortedList;
    }

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    protected boolean isExist(Object searchKey) {
        return storageMapResume.containsValue(searchKey);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return getKey(storageMapResume, (String) searchKey);
    }

    protected void doSave(Resume r, Object searchKey) {
        storageMapResume.put(r, (String) searchKey);
    }

    @Override
    protected void doDelete(Object searchKey) {
        storageMapResume.remove(getKey(storageMapResume, (String) searchKey));
    }

    @Override
    protected void doUpdate(Resume r, Object searchKey) {
        storageMapResume.put(r, (String) searchKey);
    }

    @Override
    public String toString() {
        return "storageMapResume=" + storageMapResume;
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
}
