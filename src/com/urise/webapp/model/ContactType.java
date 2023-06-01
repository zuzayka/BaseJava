package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.EnumMap;

public enum ContactType {
//    PHONE,
//    SKYPE,
//    NET_PROFILE,
//    HOME_PAGE;

    PHONE("Телефон"),
    SKYPE("Скайп"),
    NET_PROFILE("Профили"),
    HOME_PAGE("Домашняя страница");

    private final String title;
    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    private static String phone;
    private static String skype;
    private static String homePage;
    private static ArrayList<String> netProfile;
    static final EnumMap<ContactType, AbstractSection> contactType = new EnumMap<>(ContactType.class);

    public static void fillContact() {
        contactType.put(ContactType.PHONE, new TextSection(phone));
        contactType.put(ContactType.SKYPE, new TextSection(skype));
        contactType.put(ContactType.HOME_PAGE, new TextSection(homePage));
        contactType.put(ContactType.NET_PROFILE, new ListSection(netProfile));
    }
}

