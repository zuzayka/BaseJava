package com.urise.webapp;

import com.urise.webapp.model.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.urise.webapp.model.ContactType.*;
import static com.urise.webapp.model.SectionType.*;

public class ResumeTestData {
    public static void main(String[] args) {
        final String UUID_1 = "uuid1";
        final String FULL_NAME_1 = "Григорий Кислин";
        final Resume R = new Resume(UUID_1, FULL_NAME_1);

        final LocalDate startMFTIS = LocalDate.of(1984, 9, 1);
        final LocalDate stopMFTIS = LocalDate.of(1987, 6, 1);
        final String titleMFTIS = "";
        final String descriptionMFTIS = "Закончил с отличием";
        final Period periodMFTIS = new Period(startMFTIS, stopMFTIS, titleMFTIS, descriptionMFTIS);
        final String nameMFTIS = "Заочная физико-техническая школа при МФТИ";
        final String webSiteMFTIS = "https://mipt.ru";
        final ArrayList<Period> listMFTIS = new ArrayList<>();
        listMFTIS.add(periodMFTIS);
        final Organization orgMFTIS= new Organization(nameMFTIS, webSiteMFTIS, listMFTIS);

        final LocalDate startSPBU1 = LocalDate.of(1987, 9, 1);
        final LocalDate stopSPBU1 = LocalDate.of(1993, 7, 1);
        final String titleSPBU1 = "Инженер (программист Fortran, C)";
        final String descriptionSPBU1 = "";
        final Period periodSPBU1 = new Period(startSPBU1, stopSPBU1, titleSPBU1, descriptionSPBU1);

        final LocalDate startSPBU2 = LocalDate.of(1993, 9, 1);
        final LocalDate stopSPBU2 = LocalDate.of(1996, 7, 1);
        final String titleSPBU2 = "Аспирантура (программист С, С++))";
        final String descriptionSPBU2 = "";
        final Period periodSPBU2 = new Period(startSPBU2, stopSPBU2, titleSPBU2, descriptionSPBU2);

        final String nameSPBU = "Санкт-Петербургский национальный исследовательский университет " +
                "информационных технологий, механики и оптики";
        final String webSiteSPBU = "http://www.ifmo.ru";
        final ArrayList<Period> listSPBU = new ArrayList<>();
        listSPBU.add(periodSPBU1);
        listSPBU.add(periodSPBU2);
        final Organization orgSPBU= new Organization(nameSPBU, webSiteSPBU, listSPBU);

        final LocalDate startAlcatel = LocalDate.of(1997, 9, 1);
        final LocalDate stopAlcatel = LocalDate.of(2005, 1, 1);
        final String titleAlcatel = "Инженер по аппаратному и программному тестированию";
        final String descriptionAlcatel = "";
        final Period periodAlcatel = new Period(startAlcatel, stopAlcatel, titleAlcatel, descriptionAlcatel);
        final String nameAlcatel = "Alcatel";
        final String webSiteAlcatel = "http://www.alcatel.ru";
        final ArrayList<Period> listAlcatel = new ArrayList<>();
        listAlcatel.add(periodAlcatel);
        final Organization orgAlcatel= new Organization(nameAlcatel, webSiteAlcatel, listAlcatel);
        System.out.println(orgAlcatel);
        ArrayList<Organization> experienceList = new ArrayList<>();
        experienceList.add(orgAlcatel);
        ArrayList<Organization> educationList = new ArrayList<>();
        educationList.add(orgMFTIS);
        educationList.add(orgSPBU);
        ArrayList<String> achievementList = new ArrayList<>();
        achievementList.add("Организация команды и успешная реализация Java проектов для сторонних заказчиков: " +
                "приложения автопарк на стеке Spring Cloud/микросервисы, система мониторинга показателей " +
                "спортсменов на Spring Boot, участие в проекте МЭШ на Play-2, многомодульный Spring Boot + " +
                "Vaadin проект для комплексных DIY смет");
        achievementList.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\"," +
                " \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP)." +
                " Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов." +
                " Более 3500 выпускников.");
        achievementList.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами" +
                " Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        ArrayList<String> qualificationList = new ArrayList<>();
        qualificationList.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualificationList.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        qualificationList.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2," +
                        " Oracle, MySQL, SQLite, MS SQL, HSQLDB");


        R.contactType.put(PHONE, "+7(921) 855-0482");
        R.contactType.put(SKYPE, "skype:grigory.kislin");
        R.contactType.put(NET_PROFILE, "https://github.com/gkislin");
        R.contactType.put(HOME_PAGE, "http://gkislin.ru/");

        R.sectionType.put(PERSONAL, new TextSection("Аналитический склад ума, сильная логика, креативность," +
                " инициативность. Пурист кода и архитектуры"));
        R.sectionType.put(OBJECTIVE, new TextSection("Ведущий стажировок и корпоративного обучения по Java Web" +
                " и Enterprise технологиям"));
        R.sectionType.put(EXPERIENCE, new OrganizationSection(experienceList));
        R.sectionType.put(EDUCATION, new OrganizationSection(educationList));
        R.sectionType.put(ACHIEVEMENT, new ListSection(achievementList));
        R.sectionType.put(QUALIFICATIONS, new ListSection(qualificationList));
        System.out.println(R);
        Date date = new Date();
        System.out.println(date);
        System.out.println(System.currentTimeMillis() );
        Calendar calendar = Calendar.getInstance();
        System.out.println(calendar.getTime());
        LocalDate ld = LocalDate.now();
        LocalTime lt = LocalTime.now();
        LocalDateTime ldt = LocalDateTime.of(ld, lt);
        System.out.println(ldt);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/hh:mm");
        System.out.println( sdf.format(date));
        System.out.println( sdf.format(2000003463334L));
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd/hh:mm");

        System.out.println(dtf.format(ldt));
    }
}
