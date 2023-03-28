package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
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
    public Resume[] getAll() {
        return storageList.toArray(new Resume[0]);
    }

    @Override
    protected Object getSearchKey(String uuid) {
        for (int i = 0; i < storageList.size(); i++) {
            if (uuid.equals(storageList.get(i).getUuid())) {
                return i;
            }
        }
        return -1;
    }

    protected boolean isExist(Object searchKey) {
        return (int) (searchKey) >= 0;
    }

    @Override
    protected Resume doGet(Object searchKey) {
        int index = (int) searchKey;
        return storageList.get(index);
    }

    protected void doSave(Resume r, Object searchKey) {
        storageList.add(r);
    }

    @Override
    protected void doDelete(Object searchKey) {
        int index = (int) searchKey;
        storageList.remove(index);
    }

    @Override
    protected void doUpdate(Resume r, Object searchKey) {
        storageList.set((int) searchKey, r);
    }

    @Override
    public String toString() {
        return "ListStorage{" + "storageList=" + storageList + '}';
    }
}
