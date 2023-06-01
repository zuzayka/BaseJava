package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.EnumMap;

public enum SectionType {
    PERSONAL("Личные качества"),
    OBJECTIVE("Позиция"),
    ACHIEVEMENT("Достижения"),
    QUALIFICATIONS("Квалификация"),
    EXPERIENCE("Опыт работы"),
    EDUCATION("Образование");

    private final String title;
    SectionType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    private static String personal;
    private static String objective;
    private static ArrayList<Organization> experience;
    private static ArrayList<Organization> education;
    private static ArrayList<String> qualifications;
    private static ArrayList<String> achievement;
    static final EnumMap<SectionType, AbstractSection> sectionType = new EnumMap<>(SectionType.class);

    public static void fillSection() {
        sectionType.put(SectionType.PERSONAL, new TextSection(personal));
        sectionType.put(SectionType.OBJECTIVE, new TextSection(objective));
        sectionType.put(SectionType.EXPERIENCE, new OrganizationSection(experience));
        sectionType.put(SectionType.EDUCATION, new OrganizationSection(education));
        sectionType.put(SectionType.QUALIFICATIONS, new ListSection(qualifications));
        sectionType.put(SectionType.ACHIEVEMENT, new ListSection(achievement));
    }
}
