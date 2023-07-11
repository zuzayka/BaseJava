package com.urise.webapp;

import com.urise.webapp.model.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.urise.webapp.model.ContactType.*;
import static com.urise.webapp.model.SectionType.*;

public class ResumeTestData implements Serializable {
    public static Resume resumeFill(String uuid, String fullName) {
        Resume r = new Resume(uuid, fullName);

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
        r.addSection(EXPERIENCE, new OrganizationSection(experienceList));

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

        r.addContact(PHONE, "+7(921) 855-0482");
        r.addContact(SKYPE, "skype:grigory.kislin");
        r.addContact(NET_PROFILE, "https://github.com/gkislin");
        r.addContact(HOME_PAGE, "http://gkislin.ru/");

        r.addSection(PERSONAL, new TextSection("Аналитический склад ума, сильная логика, креативность," + " инициативность. Пурист кода и архитектуры"));
        r.addSection(OBJECTIVE, new TextSection("Ведущий стажировок и корпоративного обучения по Java Web" + " и Enterprise технологиям"));
        return r;
    }

//    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    public static void main(String[] args) {
        Resume r = resumeFill("uuid1", "Григорий Кислин");
        System.out.println(r);
    }
}
