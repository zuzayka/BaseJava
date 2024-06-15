package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {
    protected List<Resume> storageList = new ArrayList<>();

    @Override
    public int getSize() {
        return storageList.size();
    }

    @Override
    public void clear() {
        storageList.clear();
    }

    @Override
    public boolean isUuidInStorage(String uuid) {
        return false;
    }

    @Override
    protected List<Resume> doCopyAll() {
        return new ArrayList<>(storageList);
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < storageList.size(); i++) {
            if (uuid.equals(storageList.get(i).getUuid())) {
                return i;
            }
        }
        return -1;
    }

    protected boolean isExist(Integer searchKey) {
        return searchKey >= 0;
    }

    @Override
    protected Resume doGet(Integer searchKey) {
        return storageList.get(searchKey);
    }

    protected void doSave(Resume r, Integer searchKey) {
        storageList.add(r);
    }

    @Override
    protected void doDelete(Integer searchKey) {
        storageList.remove(searchKey.intValue());
    }

    @Override
    protected void doUpdate(Resume r, Integer searchKey) {
        storageList.set(searchKey, r);
    }

    @Override
    public String toString() {
        return "ListStorage{" + "storageList=" + storageList + '}';
    }

}
