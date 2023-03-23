package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    protected List<Resume> storageList = new ArrayList<>();

    @Override
    public void clear() {
        storageList.clear();
        size = 0;
    }

    @Override
    public Resume[] getAll() {
        return storageList.toArray(new Resume[0]);
    }

    @Override
    protected Object getSearchKey(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storageList.get(i).getUuid())) {
                return i;
            }
        }
        return -1;
    }

    protected boolean isExist(String uuid) {
        return (int) getSearchKey(uuid) >= 0;
    }


    @Override
    protected Resume doGet(Object searchKey) {
        int index = (int) searchKey;
        return storageList.get(index);
    }

    protected void doSave(Resume r, Object searchKey) {
        storageList.add(r);
        size++;
    }

    @Override
    protected void doDelete(Object searchKey) {
        int index = (int) searchKey;
        storageList.remove(index);
        size--;
    }

    @Override
    protected void doUpdate(Resume r) {
        int index = (int) getSearchKey(r.getUuid());
        storageList.set(index, r);
    }

    @Override
    public String toString() {
        return "ListStorage{" + "storageList=" + storageList + '}';
    }
}
