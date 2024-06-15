package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.List;

public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected void insertElement(Resume r, int index) {
        storage[size] = r;
    }

    @Override
    protected void fillDeletedElement(int index) {
        System.arraycopy(storage, index + 1, storage, index, size - index - 1);
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }


    public static void main(String[] args) {
        final String UUID_1 = "uuid1";
        final String UUID_2 = "uuid2";
        final String UUID_3 = "uuid3";
        final String UUID_4 = "uuid4";
        final String FULL_NAME_1 = "Petrov Petr";
        final String FULL_NAME_2 = "Ivanov Ivan";
        final String FULL_NAME_3 = "Saidova Zuleykha";
        final String FULL_NAME_4 = "Petrov Petr";
        final Resume R1 = new Resume(UUID_1, FULL_NAME_1);
        final Resume R2 = new Resume(UUID_2, FULL_NAME_2);
        final Resume R3 = new Resume(UUID_3, FULL_NAME_3);
        final Resume R4 = new Resume(UUID_4, FULL_NAME_4);
        AbstractArrayStorage aS = new ArrayStorage();
        aS.save(R2);
        aS.save(R1);
        aS.save(R3);
        List<Resume> list = aS.getAllSorted();
        System.out.println(aS + "\n" + list);
    }

    @Override
    public boolean isUuidInStorage(String uuid) {
        return false;
    }
}