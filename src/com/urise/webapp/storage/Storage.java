package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;
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

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll();
}