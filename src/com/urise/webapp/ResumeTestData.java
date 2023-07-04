package com.urise.webapp;

import com.urise.webapp.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.urise.webapp.model.ContactType.*;
import static com.urise.webapp.model.SectionType.*;

public class ResumeTestData {
    public static Resume resumeFill(String uuid, String fullName) {
        Resume r = new Resume(uuid, fullName);


        final LocalDate startMFTIS = LocalDate.of(1984, 9, 1);
        final LocalDate stopMFTIS = LocalDate.of(1987, 6, 1);
        final String titleMFTIS = "";
        final String descriptionMFTIS = "Закончил с отличием";
        final Organization.Period periodMFTIS = new Organization.Period(startMFTIS, stopMFTIS, titleMFTIS, descriptionMFTIS);
        final String nameMFTIS = "Заочная физико-техническая школа при МФТИ";
        final String webSiteMFTIS = "https://mipt.ru";
        final List<Organization.Period> listMFTIS = new ArrayList<>();
        listMFTIS.add(periodMFTIS);
        final Organization orgMFTIS = new Organization(nameMFTIS, webSiteMFTIS, listMFTIS);

        final LocalDate startSPBU1 = LocalDate.of(1987, 9, 1);
        final LocalDate stopSPBU1 = LocalDate.of(1993, 7, 1);
        final String titleSPBU1 = "Инженер (программист Fortran, C)";
        final String descriptionSPBU1 = "";
        final Organization.Period periodSPBU1 = new Organization.Period(startSPBU1, stopSPBU1, titleSPBU1, descriptionSPBU1);

        final LocalDate startSPBU2 = LocalDate.of(1993, 9, 1);
        final LocalDate stopSPBU2 = LocalDate.of(1996, 7, 1);
        final String titleSPBU2 = "Аспирантура (программист С, С++))";
        final String descriptionSPBU2 = "";
        final Organization.Period periodSPBU2 = new Organization.Period(startSPBU2, stopSPBU2, titleSPBU2, descriptionSPBU2);

        final String nameSPBU = "Санкт-Петербургский национальный исследовательский университет " + "информационных технологий, механики и оптики";
        final String webSiteSPBU = "http://www.ifmo.ru";
        final ArrayList<Organization.Period> listSPBU = new ArrayList<>();
        listSPBU.add(periodSPBU1);
        listSPBU.add(periodSPBU2);
        final Organization orgSPBU = new Organization(nameSPBU, webSiteSPBU, listSPBU);
        List<Organization> educationList = new ArrayList<>();
        educationList.add(orgMFTIS);
        educationList.add(orgSPBU);
        r.getSectionType().put(EDUCATION, new OrganizationSection(educationList));

        final LocalDate startAlcatel = LocalDate.of(1997, 9, 1);
        final LocalDate stopAlcatel = LocalDate.of(2005, 1, 1);
        final String titleAlcatel = "Инженер по аппаратному и программному тестированию";
        final String descriptionAlcatel = "";
        final Organization.Period periodAlcatel = new Organization.Period(startAlcatel, stopAlcatel, titleAlcatel, descriptionAlcatel);
        final String nameAlcatel = "Alcatel";
        final String webSiteAlcatel = "http://www.alcatel.ru";
        final List<Organization.Period> listAlcatel = new ArrayList<>();
        listAlcatel.add(periodAlcatel);
        final Organization orgAlcatel = new Organization(nameAlcatel, webSiteAlcatel, listAlcatel);
        System.out.println(orgAlcatel);
        List<Organization> experienceList = new ArrayList<>();
        experienceList.add(orgAlcatel);
        r.getSectionType().put(EXPERIENCE, new OrganizationSection(experienceList));

        final List<String> achievementList = new ArrayList<>() {{
        add("Организация команды и успешная реализация Java проектов для сторонних заказчиков: " + "приложения автопарк на стеке Spring Cloud/микросервисы, система мониторинга показателей " + "спортсменов на Spring Boot, участие в проекте МЭШ на Play-2, многомодульный Spring Boot + " + "Vaadin проект для комплексных DIY смет");
        add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\"," + " \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP)." + " Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов." + " Более 3500 выпускников.");
        add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами" + " Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        }};
        r.addSection(ACHIEVEMENT, new ListSection(achievementList));

        final List<String> qualificationList = new ArrayList<>() {{
                add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
                add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
                add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2," + " Oracle, MySQL, SQLite, MS SQL, HSQLDB");
            }};
        r.addSection(QUALIFICATIONS, new ListSection(qualificationList));

        r.getContactType().put(PHONE, "+7(921) 855-0482");
        r.getContactType().put(SKYPE, "skype:grigory.kislin");
        r.getContactType().put(NET_PROFILE, "https://github.com/gkislin");
        r.getContactType().put(HOME_PAGE, "http://gkislin.ru/");

        r.getSectionType().put(PERSONAL, new TextSection("Аналитический склад ума, сильная логика, креативность," + " инициативность. Пурист кода и архитектуры"));
        r.getSectionType().put(OBJECTIVE, new TextSection("Ведущий стажировок и корпоративного обучения по Java Web" + " и Enterprise технологиям"));
        return r;
    }

//    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    public static void main(String[] args) {
        Resume r = resumeFill("uuid1", "Григорий Кислин");
        System.out.println(r);
    }
}
