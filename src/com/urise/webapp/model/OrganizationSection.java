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
}
