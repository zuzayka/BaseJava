package com.urise.webapp.model;

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


}

