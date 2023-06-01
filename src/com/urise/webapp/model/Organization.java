package com.urise.webapp.model;

import java.util.ArrayList;

public class Organization {
    protected String name;
    protected String webSite;
    protected ArrayList<Period> periods;

    public Organization(String name,
                        String webSite, ArrayList<Period> periods) {
        this.name = name;
        this.webSite = webSite;
        this.periods = periods;
    }

    @Override
    public String toString() {
        return "Organization{" + "name='" + name + '\'' + ", webSite='" + webSite + '\'' + ", periods=" + periods + '}';
    }
}
