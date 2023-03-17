package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.Arrays;
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
        Resume[] resumes = new Resume[size];
        for (int i = 0; i < size; i++) {
            resumes[i] = storageList.get(i);
        }
        return resumes;
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
    protected Resume doGetResume(Object searchKey) {
        int index = (int) searchKey;
        return storageList.get(index);
    }

    protected void doSave(Resume r) {
        if (!isExist(r.getUuid())) {
            storageList.add(r);
            size++;
        }
    }

    @Override
    protected void doDelete(Resume r) {
        int index = (int) getSearchKey(r.getUuid());
        storageList.remove(index);
        size--;
    }

    @Override
    protected void doUpdate(Resume r){
        int index = (int) getSearchKey(r.getUuid());
        storageList.set(index, r);
    }

    @Override
    public String toString() {
        return "ListStorage{" + "storageList=" + storageList + '}';
    }

    // main for fast test ListStorage:

    public static void main(String[] args) {
        ListStorage listStorage = new ListStorage();
        final String UUID_1 = "uuid1";
        final String UUID_2 = "uuid2";
        String UUID_3 = "uuid3";
        Resume r3 = new Resume(UUID_3);
        listStorage.clear();
        listStorage.save(new Resume(UUID_1));
        listStorage.save(new Resume(UUID_2));
        listStorage.save(r3);
        System.out.println(listStorage.getSize());
        System.out.println(listStorage);
        System.out.println(Arrays.toString(listStorage.getAll()));
        System.out.println(listStorage.getResume("uuid2"));
        listStorage.delete("uuid1");
        System.out.println(listStorage.getSize());
        System.out.println(listStorage);
        listStorage.clear();
        System.out.println(listStorage.getSize());
        System.out.println(listStorage);
    }
}
