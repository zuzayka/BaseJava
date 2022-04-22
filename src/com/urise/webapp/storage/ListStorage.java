package com.urise.webapp.storage;

public class ListStorage extends AbstractStorage {
    public void get() {

    }

    @Override
    protected int findIndex(String uuid) {
        int size = storage.size();
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage.get(i).getUuid())) {
                System.out.println(i);
                return i;
            }
        }
        System.out.println("-1");
        return -1;
    }
}
