package com.urise.webapp.model;

import java.util.ArrayList;

public class OrganizationSection extends AbstractSection {
    private final ArrayList<Organization> list;

    public OrganizationSection(ArrayList<Organization> list) {
        this.list = list;
    }

    public ArrayList<Organization> getList() {
        return list;
    }

    @Override
    public String toString() {
        return "OrganizationSection{" + "list=" + list + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrganizationSection that = (OrganizationSection) o;

        return list.equals(that.list);
    }

    @Override
    public int hashCode() {
        return list.hashCode();
    }
}
