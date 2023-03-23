package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MapStorageTest {
    private Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    public static final Resume R1 = new Resume(UUID_1);
    public static final Resume R2 = new Resume(UUID_2);
    public static final Resume R3 = new Resume(UUID_3);
    public static final Resume R4 = new Resume(UUID_4);

    public MapStorageTest() {
        storage = new MapStorage();
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(R1);
        storage.save(R2);
        storage.save(R3);
    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);
    }

    @Test
    public void update() throws Exception {
        Resume newResume = new Resume(UUID_4);
        storage.save(newResume);
        assertSame(newResume, storage.getResume(UUID_4));
    }

    @Test
    public void save() {
        storage.save(R4);
        assertSize(4);
        assertEquals(R4, storage.getResume(UUID_4));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() throws Exception {
        storage.save(R1);
    }



    @Test
    public void getSize() {
        assertSize(3);
    }

    @Test
    public void get() throws Exception {
        Resume r = storage.getResume(UUID_1);
        assertEquals(r.getUuid(), UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws Exception {
        storage.delete(UUID_1);
        assertSize(2);
        storage.getResume(UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() throws Exception {
        storage.delete("dummy");
    }

    @Test
    public void getAll() {
        Resume[] array = storage.getAll();
        assertEquals(3, array.length);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.getResume("dummy");
    }

    private void assertSize(int size) {
        assertEquals(size, storage.getSize());
    }    
}