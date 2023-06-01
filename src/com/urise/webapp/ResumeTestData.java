package com.urise.webapp;

import com.urise.webapp.model.Organization;
import com.urise.webapp.model.Period;
import com.urise.webapp.model.Resume;

import java.time.LocalDate;
import java.util.ArrayList;

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
        System.out.println(orgMFTIS);

        final LocalDate startSPBU1 = LocalDate.of(1987, 9, 1);
        final LocalDate stopSPBU1 = LocalDate.of(1993, 7, 1);
        final String titleSPBU1 = "Инженер (программист Fortran, C)";
        final String descriptionSPBU1 = "";
        final Period periodSPBU1 = new Period(startSPBU1, stopSPBU1, titleSPBU1, descriptionSPBU1);
        System.out.println(periodSPBU1);

        final LocalDate startSPBU2 = LocalDate.of(1993, 9, 1);
        final LocalDate stopSPBU2 = LocalDate.of(1996, 7, 1);
        final String titleSPBU2 = "Аспирантура (программист С, С++))";
        final String descriptionSPBU2 = "";
        final Period periodSPBU2 = new Period(startSPBU2, stopSPBU2, titleSPBU2, descriptionSPBU2);
        System.out.println(periodSPBU2);
        final String nameSPBU = "Санкт-Петербургский национальный исследовательский университет " +
                "информационных технологий, механики и оптики";
        final String webSiteSPBU = "http://www.ifmo.ru";
        final ArrayList<Period> listSPBU = new ArrayList<>();
        listSPBU.add(periodSPBU1);
        listSPBU.add(periodSPBU2);
        final Organization orgSPBU= new Organization(nameSPBU, webSiteSPBU, listSPBU);
        System.out.println(orgSPBU);

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
    }
}
