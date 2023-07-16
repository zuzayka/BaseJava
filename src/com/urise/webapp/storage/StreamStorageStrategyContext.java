package com.urise.webapp.storage;

public class StreamStorageStrategyContext {
    private ObjectStreamStorageInterface objectStream;

    public ObjectStreamStorageInterface setObjectStream(ObjectStreamStorageInterface objectStream) {
        return this.objectStream = objectStream;
    }
}
