package com.urise.webapp.model;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Initial resume class
 */

public class Resume implements Comparable<Resume> {

    // Unique identifier
    private final String uuid;
    private final String fullName;
    public final Map<SectionType, AbstractSection> sectionType = new EnumMap<>(SectionType.class);
    public final Map<ContactType, String> contactType = new EnumMap<>(ContactType.class);


    public Resume() {
        this(UUID.randomUUID().toString(), UUID.randomUUID().toString());
    }

    public Resume(String fullName) {
        this(fullName, UUID.randomUUID().toString());
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(fullName, "fullName must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public AbstractSection getSectionType(SectionType type) {
        return sectionType.get(type);
    }

    public Map<ContactType, String> getContactType() {
        return contactType;
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
        return "{uuid=" + uuid + ", fullName=" + fullName + ", \nsectionType=" + sectionType + ", \ncontactType=" + contactType + "}";
    }


    @Override
    public int compareTo(Resume resume) {
        int cmp = fullName.compareTo(resume.fullName);
        return cmp != 0 ? cmp : uuid.compareTo(resume.uuid);
    }
}