package com.urise.webapp.model;

//@XmlAccessorType(XmlAccessType.FIELD)
public enum ContactType {
//    PHONE,
//    SKYPE,
//    NET_PROFILE,
//    HOME_PAGE;

    PHONE("Телефон"),
    SKYPE("Скайп") {
        @Override
        public String toHtml0(String value) {
            return "<a href='skype: " + value + "'>" + getTitle() + " </a>";
        }
    },
    NET_PROFILE("Профили")
            {
        @Override
        public String toHtml0(String value) {
            return "<a href='" + value + "'>" + getTitle() + ": " + value + " </a>";
        }
    },


    HOME_PAGE("Домашняя страница")
            {
    public String toHtml0(String value) {
        return "<a href='" + value + "'>" + getTitle() + " </a>";
    }
    };

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
        return "<a href='" + href + "'" + title + "/a>";
    }
}

