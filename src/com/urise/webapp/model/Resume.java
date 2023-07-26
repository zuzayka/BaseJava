package com.urise.webapp.model;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Initial resume class
 */

//@XmlRootElement
public class Resume implements Comparable<Resume>, Serializable {
    private static final long serialVersionUID = 1L;
    // Unique identifier
    private final String uuid;
    private final String fullName;
    private final Map<SectionType, AbstractSection> sectionType = new EnumMap<>(SectionType.class);
    private final Map<ContactType, String> contactType = new EnumMap<>(ContactType.class);


    public Map<SectionType, AbstractSection> getSectionType() {
        return sectionType;
    }

    public Map<ContactType, String> getContactType() {
        return contactType;
    }

    public Resume() {
        this(UUID.randomUUID().toString(), UUID.randomUUID().toString());
    }

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
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

//    public AbstractSection getSectionType(SectionType type) {
//        return sectionType.get(type);
//    }
//
//    public Map<ContactType, String> getContactType() {
//        return contactType;
//    }

    public String getContact(ContactType type) {
        return contactType.get(type);
    }

    public AbstractSection getSection(SectionType type) {
        return sectionType.get(type);
    }

    public void addContact(ContactType type, String value) {
        contactType.put(type, value);
    }

    public void addSection(SectionType type, AbstractSection section) {
        sectionType.put(type, section);
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

//    public static void resumeFill(Resume R) {
//        final LocalDate startMFTIS = LocalDate.of(1984, 9, 1);
//        final LocalDate stopMFTIS = LocalDate.of(1987, 6, 1);
//        final String titleMFTIS = "";
//        final String descriptionMFTIS = "Закончил с отличием";
//        final Organization.Period periodMFTIS = new Organization.Period(startMFTIS, stopMFTIS, titleMFTIS, descriptionMFTIS);
//        final String nameMFTIS = "Заочная физико-техническая школа при МФТИ";
//        final String webSiteMFTIS = "https://mipt.ru";
//        final ArrayList<Organization.Period> listMFTIS = new ArrayList<>();
//        listMFTIS.add(periodMFTIS);
//        final Organization orgMFTIS = new Organization(nameMFTIS, webSiteMFTIS, listMFTIS);
//
//        final LocalDate startSPBU1 = LocalDate.of(1987, 9, 1);
//        final LocalDate stopSPBU1 = LocalDate.of(1993, 7, 1);
//        final String titleSPBU1 = "Инженер (программист Fortran, C)";
//        final String descriptionSPBU1 = "";
//        final Organization.Period periodSPBU1 = new Organization.Period(startSPBU1, stopSPBU1, titleSPBU1, descriptionSPBU1);
//
//        final LocalDate startSPBU2 = LocalDate.of(1993, 9, 1);
//        final LocalDate stopSPBU2 = LocalDate.of(1996, 7, 1);
//        final String titleSPBU2 = "Аспирантура (программист С, С++))";
//        final String descriptionSPBU2 = "";
//        final Organization.Period periodSPBU2 = new Organization.Period(startSPBU2, stopSPBU2, titleSPBU2, descriptionSPBU2);
//
//        final String nameSPBU = "Санкт-Петербургский национальный исследовательский университет " + "информационных технологий, механики и оптики";
//        final String webSiteSPBU = "http://www.ifmo.ru";
//        final ArrayList<Organization.Period> listSPBU = new ArrayList<>();
//        listSPBU.add(periodSPBU1);
//        listSPBU.add(periodSPBU2);
//        final Organization orgSPBU = new Organization(nameSPBU, webSiteSPBU, listSPBU);
//
//        final LocalDate startAlcatel = LocalDate.of(1997, 9, 1);
//        final LocalDate stopAlcatel = LocalDate.of(2005, 1, 1);
//        final String titleAlcatel = "Инженер по аппаратному и программному тестированию";
//        final String descriptionAlcatel = "";
//        final Organization.Period periodAlcatel = new Organization.Period(startAlcatel, stopAlcatel, titleAlcatel, descriptionAlcatel);
//        final String nameAlcatel = "Alcatel";
//        final String webSiteAlcatel = "http://www.alcatel.ru";
//        final ArrayList<Organization.Period> listAlcatel = new ArrayList<>();
//        listAlcatel.add(periodAlcatel);
//        final Organization orgAlcatel = new Organization(nameAlcatel, webSiteAlcatel, listAlcatel);
//        System.out.println(orgAlcatel);
//        ArrayList<Organization> experienceList = new ArrayList<>();
//        experienceList.add(orgAlcatel);
//        ArrayList<Organization> educationList = new ArrayList<>();
//        educationList.add(orgMFTIS);
//        educationList.add(orgSPBU);
//        ArrayList<String> achievementList = new ArrayList<>();
//        achievementList.add("Организация команды и успешная реализация Java проектов для сторонних заказчиков: " + "приложения автопарк на стеке Spring Cloud/микросервисы, система мониторинга показателей " + "спортсменов на Spring Boot, участие в проекте МЭШ на Play-2, многомодульный Spring Boot + " + "Vaadin проект для комплексных DIY смет");
//        achievementList.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\"," + " \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP)." + " Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов." + " Более 3500 выпускников.");
//        achievementList.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами" + " Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
//        ArrayList<String> qualificationList = new ArrayList<>();
//        qualificationList.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
//        qualificationList.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
//        qualificationList.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2," + " Oracle, MySQL, SQLite, MS SQL, HSQLDB");
//    }
}