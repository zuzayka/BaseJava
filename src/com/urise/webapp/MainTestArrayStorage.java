package com.urise.webapp;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.ArrayStorage;
import com.urise.webapp.storage.SortedArrayStorage;

import java.util.Arrays;

/**
 * Test for your com.urise.webapp.storage.ArrayStorage implementation
 */
public class MainTestArrayStorage {
    private final static ArrayStorage ARRAY_STORAGE = new ArrayStorage();
    private final static SortedArrayStorage SORTED_ARRAY_STORAGE = new SortedArrayStorage();

    public static void main(String[] args) {
        final Resume r1 = new Resume();
        //r1.setUuid("uuid1");
        final Resume r2 = new Resume();
        //r2.setUuid("uuid2");
        final Resume r3 = new Resume();
        //r3.setUuid("uuid3");
        final Resume r4 = new Resume();
        //r4.setUuid("uuid4");

        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r3);
        ARRAY_STORAGE.save(r4);

        SORTED_ARRAY_STORAGE.save(r1);
        SORTED_ARRAY_STORAGE.save(r2);
        SORTED_ARRAY_STORAGE.save(r3);
        SORTED_ARRAY_STORAGE.save(r4);

        System.out.println("Get r1: " + ARRAY_STORAGE.getResume(r1.getUuid()));
        System.out.println(Arrays.toString(ARRAY_STORAGE.getAll()));
        System.out.println("Size: " + ARRAY_STORAGE.getSize());

        System.out.println("Get r1: " + SORTED_ARRAY_STORAGE.getResume(r1.getUuid()));
        System.out.println(Arrays.toString(SORTED_ARRAY_STORAGE.getAll()));
        System.out.println("Size: " + SORTED_ARRAY_STORAGE.getSize());

        System.out.println("Get dummy: " + ARRAY_STORAGE.getResume("dummy"));

        printAll();

        ARRAY_STORAGE.update(r1);
        ARRAY_STORAGE.update(r4);

        ARRAY_STORAGE.delete(r4.getUuid());
        printAll();

        ARRAY_STORAGE.delete(r1.getUuid());
        printAll();
        ARRAY_STORAGE.delete(r1.getUuid());

        ARRAY_STORAGE.clear();
        printAll();

        System.out.println("Size: " + ARRAY_STORAGE.getSize());
    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAll()) {
            System.out.println(r);
        }
    }
}