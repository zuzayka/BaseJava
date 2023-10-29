package com.urise.webapp.util;

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


    public static Config getInstance() {
        return INSTANCE;
    }

    private Config() {
        try (InputStream is = new FileInputStream(PROPERTY)){
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
