package com.urise.webapp.model;

import java.util.EnumMap;
import java.util.Objects;
import java.util.UUID;

/**
 * Initial resume class
 */
//public class Resume implements Comparable<Resume> {
public class Resume implements Comparable<Resume> {

    // Unique identifier
    private final String uuid;
    private final String fullName;
    private final EnumMap<SectionType, AbstractSection> sectionsMap = SectionType.sectionType;
    private final EnumMap<ContactType, AbstractSection> contactMap = ContactType.contactType;

    public Resume() {
        this(UUID.randomUUID().toString(), UUID.randomUUID().toString());
    }

    public Resume(String fullName) {
        this(fullName, UUID.randomUUID().toString());
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(fullName, "uuid must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Resume resume = (Resume) o;

        return (uuid.equals(resume.uuid) && fullName.equals(resume.fullName));
    }

    @Override
    public int hashCode() {
        String hashString = uuid + fullName;
        return hashString.hashCode();
    }

    @Override
    public String toString() {
        return "{uuid=" + uuid + ", fullName=" + fullName + "}";
    }

    @Override
    public int compareTo(Resume resume) {
        int cmp = fullName.compareTo(resume.fullName);
        return cmp != 0 ? cmp : uuid.compareTo(resume.uuid);
    }

    public static void main(String[] args) {
        final String UUID_1 = "uuid1";
        final String UUID_2 = "uuid2";
        final String UUID_3 = "uuid3";
        final String UUID_4 = "uuid4";
        final String FULL_NAME_1 = "Petrov Petr";
        final String FULL_NAME_2 = "Ivanov Ivan";
        final String FULL_NAME_3 = "Saidova Zuleykha";
        final String FULL_NAME_4 = "Petrov Petr";
        final Resume R1 = new Resume(UUID_1, FULL_NAME_1);
        final Resume R2 = new Resume(UUID_2, FULL_NAME_2);
        final Resume R3 = new Resume(UUID_3, FULL_NAME_3);
        final Resume R4 = new Resume(UUID_4, FULL_NAME_4);
    }
}