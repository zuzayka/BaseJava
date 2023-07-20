package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> implements Serializable {
    private final File directory;
    private final SerializerStraregy straregy;

    protected FileStorage(File directory, SerializerStraregy straregy) {
        Objects.requireNonNull(directory, "directory mast not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + "is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + "is not readable/writable");
        }
        this.directory = directory;
        this.straregy = straregy;
    }

    @Override
    public int getSize() {
        return arrayFile().length;
    }

    @Override
    public void clear() {
        for (File f : arrayFile()) {
            doDelete(f);
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
            return straregy.doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File read error", file.getName(), e);
        }
    }

    @Override
    protected void doSave(Resume r, File file) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new StorageException("Couldn't create file " + file.getAbsolutePath(), file.getName(), e);
        }
        doUpdate(r, file);
    }

    @Override
    protected void doDelete(File file) {
        if (!file.delete()) {
            throw new StorageException("IO error", file.getName());
        }
    }

    @Override
    protected void doUpdate(Resume r, File file) {
        try {
            straregy.doWrite(r, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File write error", file.getName(), e);
        }
    }

    @Override
    protected List<Resume> doCopyAll() {
        List<Resume> list = new ArrayList<>();
        for (File f : arrayFile()) {
            list.add(doGet(f));
        }
        return list;
    }

    private File[] arrayFile() {
        File[] array = directory.listFiles();
        if (array != null) {
            return array;
        } else {
            throw new StorageException("IO error", array);
        }
    }

//    protected abstract void doWrite(Resume r, OutputStream os) throws IOException;
//
//    protected abstract Resume doRead(InputStream is) throws IOException;
}
