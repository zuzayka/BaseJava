package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (!Objects.equals(name, that.name)) return false;
        if (!Objects.equals(webSite, that.webSite)) return false;
        return Objects.equals(periods, that.periods);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (webSite != null ? webSite.hashCode() : 0);
        result = 31 * result + (periods != null ? periods.hashCode() : 0);
        return result;
    }
}
