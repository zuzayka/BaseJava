package com.urise.webapp.util;

import com.urise.webapp.storage.SqlStorage;
import com.urise.webapp.storage.Storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    public static final File PROPERTY = new File("config/resumes.property");
    private static final Config INSTANCE = new Config();
    private Properties props = new Properties();
    private File storageDir;
    private Storage storage;


    public static Config getInstance() {
        return INSTANCE;
    }

    public Storage getStorage() {
        return storage;
    }

    private Config() {
        try (InputStream is = new FileInputStream(PROPERTY)){
            storage = new SqlStorage("jdbc:postgresql://localhost:5432/resumes", "postgres", "");
            props.load(is);
            storageDir = new File(props.getProperty("storage.dir"));
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file " + PROPERTY.getAbsolutePath());
        }
    }

    public Properties getProps() {
        return props;
    }

    public File getStorageDir() {
        return storageDir;
    }
}

