package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.List;

/**
 * Array based storage for Resumes
 * 7 methods
 */
public interface Storage {
    int getSize();

    void clear();

    void update(Resume r);

    void save(Resume r);

    Resume getResume(String uuid);

    void delete(String uuid);

    //    Resume[] getAll();
    List<Resume> getAllSorted();

    boolean isUuidInStorage(String uuid);
}