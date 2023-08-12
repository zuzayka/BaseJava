package com.urise.webapp.storage;

import com.urise.webapp.ResumeTestData;
import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public abstract class AbstractStorageTest {
    protected static final File STORAGE_DIR = new File("/home/miux/Java/basejava/storage");
    protected Storage storage;

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final String FULL_NAME_1 = "Petrov Petr";
    private static final String FULL_NAME_2 = "Ivanov Ivan";
    private static final String FULL_NAME_3 = "Saidova Zuleykha";
    private static final String FULL_NAME_4 = "Petrov Petr";
    public static final Resume R1 = ResumeTestData.resumeFill(UUID_1, FULL_NAME_1);
    public static final Resume R2 = ResumeTestData.resumeFill(UUID_2, FULL_NAME_2);
    public static final Resume R3 = ResumeTestData.resumeFill(UUID_3, FULL_NAME_3);
    public static final Resume R4 = ResumeTestData.resumeFill(UUID_4, FULL_NAME_4);

//    public static final Resume R1 = new Resume(UUID_1, FULL_NAME_1);
//    public static final Resume R2 = new Resume(UUID_2, FULL_NAME_2);
//    public static final Resume R3 = new Resume(UUID_3, FULL_NAME_3);
//    public static final Resume R4 = new Resume(UUID_4, FULL_NAME_4);

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(R2);
        storage.save(R1);
        storage.save(R3);
    }

    @Test
    public void getSize() throws Exception {
        assertSize(3);
    }


    @Test
    public void get() throws Exception {
        assertGet(R1);
        assertGet(R2);
        assertGet(R3);
//        Resume r = storage.getResume(UUID_1);
//        assertEquals(r.getUuid(), UUID_1);
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        assertSize(0);
    }

    @Test
    public void save() throws Exception {
        storage.save(R4);
        assertSize(4);
//        assertEquals(R4, storage.getResume(UUID_4));
        assertGet(R4);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() throws Exception {
        storage.save(R1);
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
    public void update() throws Exception {
        Resume newResume = ResumeTestData.resumeFill(UUID_4, FULL_NAME_4);
        storage.save(newResume);
        storage.update(newResume);
        assertTrue(newResume.equals(storage.getResume(UUID_4)));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws Exception {
        storage.delete(UUID_1);
        storage.update(R1);
    }

    @Test
    public void getAllSorted() throws Exception {
        List<Resume> list = storage.getAllSorted();
        assertEquals(3, list.size());
        if (!(storage instanceof MapStorage)) {
            assertEquals(R1, list.get(1));
            assertEquals(R2, list.get(0));
            assertEquals(R3, list.get(2));
        }
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.getResume("dummy");
    }

    private void assertGet(Resume r) {
        assertEquals(r, storage.getResume(r.getUuid()));
    }

    private void assertSize(int size) {
        assertEquals(size, storage.getSize());
    }

//    @Test(expected = StorageException.class)
//    public void saveOverflow() throws Exception {
//        Assume.assumeFalse((storage instanceof ListStorage) || (storage instanceof MapStorage)
//                || (storage instanceof MapResumeStorage));
//        try {
//            for (int i = 0; i <= AbstractArrayStorage.STORAGE_LIMIT; i++) {
//                storage.save(new Resume());
//            }
//        } catch (StorageException e) {
//            e.printStackTrace();
////            Assert.fail();
//        }
//        storage.save(new Resume());
//    }
}