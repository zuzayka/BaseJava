package com.urise.webapp.model;

import java.util.ArrayList;


public class ListSection extends AbstractSection {
    private final ArrayList<String> list;

    public ListSection(ArrayList<String> list) {
        this.list = list;
    }

    public ArrayList<String> getList() {
        return list;
    }
}
