package com.urise.webapp.model;

//@XmlAccessorType(XmlAccessType.FIELD)
public enum ContactType {
//    PHONE,
//    SKYPE,
//    NET_PROFILE,
//    HOME_PAGE;

    PHONE("Телефон") {
        @Override
        public String toHtml (String value) {
            return "<a href='phone: " + value + "'>" + value + " /a>";
        }
    },
    SKYPE("Скайп") {
        @Override
        public String toHtml0 (String value) {
            return "<a href='skype: " + value + "'>" + value + " /a>";
        }
    },
    NET_PROFILE("Профили"),
    HOME_PAGE("Домашняя страница");

    private String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String toHtml0(String value) {
        return title + ":" + value;
    }

    public String toHtml(String value) {
        return (value == null) ? "" : toHtml0(value);
    }

    public String toLink(String href) {
        return toLink(href, title);
    }

    public static String toLink(String href, String title) {
        return "<a href='" + href + "'>" + title + "</a>";
    }
}

