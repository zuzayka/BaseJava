package com.urise.webapp.storage;

import com.urise.webapp.ResumeTestData;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractPathStorage extends AbstractStorage<Path> {
    private Path directory;

    protected AbstractPathStorage(Path directory) {
//        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory mast not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(directory + "is not directory");
        }
    }

    @Override
    public int getSize() {
        try {
            return (int) Files.list(directory).count();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::doDelete);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null);
        }
    }


    @Override
    protected Resume doGet(Path path) {
        try {
            return doRead(new BufferedInputStream(new FileInputStream(path.toFile())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void doSave(Resume r, Path path) {
        try {
            doWrite(r, new BufferedOutputStream(new FileOutputStream(path.toFile())));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(Path path) {
        if (path.toFile().exists()) {
            path.toFile().delete();
        }
    }

    @Override
    protected void doUpdate(Resume r, Path path) {
        doDelete(path);
        doSave(r, path);
    }

    @Override
    protected Path getSearchKey(String pathString) {
        return Paths.get(pathString);
    }

    @Override
    protected boolean isExist(Path searchKey) {
        return false;
    }

    @Override
    protected List<Resume> doCopyAll() {
        List<Resume> list = new ArrayList<>();
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(directory)) {
            for (Path p : directoryStream) {
                list.add(doGet(p));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    protected abstract void doWrite(Resume r, OutputStream os) throws IOException;

    protected abstract Resume doRead(InputStream is) throws IOException;

    public static void main(String[] args) {
        final Path STORAGE_DIR = new File("/home/miux/Java/basejava/storage").toPath();
        Storage storage = new ObjectStreamStorage(STORAGE_DIR.toFile());
        final String UUID_1 = "uuid1";
        final String UUID_2 = "uuid2";
        final String UUID_3 = "uuid3";
        final String UUID_4 = "uuid4";
        final String FULL_NAME_1 = "Petrov Petr";
        final String FULL_NAME_2 = "Ivanov Ivan";
        final String FULL_NAME_3 = "Saidova Zuleykha";
        final String FULL_NAME_4 = "Petrov Petr";
        final Resume R1 = ResumeTestData.resumeFill(UUID_1, FULL_NAME_1);
        final Resume R2 = ResumeTestData.resumeFill(UUID_2, FULL_NAME_2);
        final Resume R3 = ResumeTestData.resumeFill(UUID_3, FULL_NAME_3);
        final Resume R4 = ResumeTestData.resumeFill(UUID_4, FULL_NAME_4);
        storage.clear();
        storage.save(R2);
        storage.save(R1);
        storage.save(R3);
    }
}
