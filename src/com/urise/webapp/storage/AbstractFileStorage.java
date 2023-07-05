package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory mast not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + "is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + "is not readable/writable");
        }
        this.directory = directory;
    }

    @Override
    public int getSize() {
        File[] arrayFile = directory.listFiles();
        if (arrayFile != null) {
            return arrayFile.length;
        } else {
            throw new StorageException("IO error", arrayFile);
        }
    }

    @Override
    public void clear() {
        File[] arrayFile = directory.listFiles();
        if (arrayFile != null) {
            for (File f : arrayFile) {
                doDelete(f);
            }
        } else {
            throw new StorageException("IO error", arrayFile);
        }
    }


    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected Resume doGet(File file) {
        try {
            return doRead(file.getCanonicalFile());
        } catch (IOException e) {
            throw new StorageException("File read error", file.getName(), e);
        }
    }

    @Override
    protected void doSave(Resume r, File file) {
        try {
            file.createNewFile();
            doWrite(r, file);
        } catch (IOException e) {
            throw new StorageException("Couldn't create file " + file.getAbsolutePath(), file.getName(), e);
        }
        doUpdate(r, file);
    }

    @Override
    protected void doDelete(File file) {
        if (!file.delete()){
            throw new StorageException("IO error", file.getName());
        } else  {
            file.delete();
        }
    }

    @Override
    protected void doUpdate(Resume r, File file) {
        try {
            doWrite(r, file);
        } catch (IOException e) {
            throw new StorageException("File write error", file.getName(), e);
        }
    }

    @Override
    protected List<Resume> doCopyAll() {
        File[] arrayFile = directory.listFiles();
        List<Resume> list = new ArrayList<>();
        if (arrayFile != null) {
            for (File f : arrayFile) {
                    list.add(doGet(f));
            }
        } else {
            throw new StorageException("Directory read error", arrayFile);
        }
        return list;
    }

    protected abstract void doWrite(Resume r, File file) throws IOException;

    protected abstract Resume doRead(File file) throws IOException;
}
