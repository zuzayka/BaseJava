package com.urise.webapp.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class OrganizationSection extends AbstractSection {
    private static final long serialVersionUID = 1L;
    private final List<Organization> list;

    public OrganizationSection(Organization... list) {
        this(Arrays.asList(list));
    }

    public OrganizationSection(List<Organization> list) {
        Objects.requireNonNull(list, "List<Organizations> mast not be null");  //задаем условие
        this.list = list;
    }

    public List<Organization> getList() {
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
