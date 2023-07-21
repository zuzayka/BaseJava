package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.strategy.SerializerStraregy;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PathStorage extends AbstractStorage<Path> {
    private Path directory;
    private SerializerStraregy straregy;

    protected PathStorage(String dir, SerializerStraregy straregy) {
        Objects.requireNonNull(directory, "directory mast not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(directory + "is not directory");
        }
        directory = Paths.get(dir);
        this.straregy = straregy;
    }

    @Override
    public int getSize() {
        try {
            return (int) Files.list(directory).count();
        } catch (IOException e) {
            throw new StorageException("Directory read error", null);
        }
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
            return straregy.doRead(new BufferedInputStream(new FileInputStream(path.toFile())));
        } catch (IOException e) {
            throw new StorageException("Path read error", path.getFileName().toString(), e);
        }
    }

    @Override
    protected void doSave(Resume r, Path path) {
        try {
            Files.createFile(path);
            straregy.doWrite(r, new BufferedOutputStream(new FileOutputStream(path.toFile())));
        } catch (IOException e) {
            throw new StorageException("Couldn't create Path " + path.toFile().getAbsolutePath(), path.getFileName().toString(), e);
        }
    }

    @Override
    protected void doDelete(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("Path delete error", path.getFileName().toString());
        }
    }

    @Override
    protected void doUpdate(Resume r, Path path) {
        try {
            straregy.doWrite(r, new BufferedOutputStream(new FileOutputStream(path.toFile())));
        } catch (IOException e) {
            throw new StorageException("File write error", path.getFileName().toString(), e);
        }
    }

    @Override
    protected Path getSearchKey(String pathString) {
        return Paths.get(pathString);
    }

    @Override
    protected boolean isExist(Path searchKey) {
        return Files.exists(searchKey);
    }

    @Override
    protected List<Resume> doCopyAll() {
        List<Resume> list = new ArrayList<>();
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(directory)) {
            for (Path p : directoryStream) {
                list.add(doGet(p));
            }
        } catch (IOException e) {
            throw new StorageException("Directory read error", null);
        }
        return list;
    }

//    protected abstract void doWrite(Resume r, OutputStream os) throws IOException;
//
//    protected abstract Resume doRead(InputStream is) throws IOException;

/*    public static void main(String[] args) {
        final Path STORAGE_DIR = new File("/home/miux/Java/basejava/storage").toPath();
        final SerializerStraregy STORAGE_SERIALIZER = new ObjectStreamSerializer();
        Storage storage = new ObjectStreamStorage(STORAGE_DIR.toFile(), STORAGE_SERIALIZER);
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
    }*/
}
