package com.urise.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public enum ContactType {
//    PHONE,
//    SKYPE,
//    NET_PROFILE,
//    HOME_PAGE;

    PHONE("Телефон"),
    SKYPE("Скайп"),
    NET_PROFILE("Профили"),
    HOME_PAGE("Домашняя страница");

    private String title;

    ContactType() {
    }

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }


}

