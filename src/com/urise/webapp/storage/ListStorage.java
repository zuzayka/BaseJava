package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
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
    public void update(Resume r) {
        int index = findIndex(r.getUuid());
        if (index >= 0) {
            storageList.set(index, r);
            return;
        }
        throw new NotExistStorageException(r.getUuid());

    }

    @Override
    public void save(Resume r) {
        int index = findIndex(r.getUuid());
        if (index >= 0) {
            throw new ExistStorageException(r.getUuid());
        } else {
            storageList.add(r);
            size++;
        }
    }

    @Override
    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            return storageList.get(index);
        }
        throw new NotExistStorageException(uuid);
    }

    @Override
    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            storageList.remove(index);
            size--;
            return;
        }
        throw new NotExistStorageException(uuid);
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
    protected int findIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storageList.get(i).getUuid())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public String toString() {
        return "ListStorage{" + "storageList=" + storageList + '}';
    }

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
        System.out.println(listStorage.get("uuid2"));
        listStorage.delete("uuid1");
        System.out.println(listStorage.getSize());
        System.out.println(listStorage);
        listStorage.clear();
        System.out.println(listStorage.getSize());
        System.out.println(listStorage);
    }
}
