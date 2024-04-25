package com.urise.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class ListSection extends AbstractSection {
    private static final long serialVersionUID = 1L;
    private List<String> list;

    public ListSection() {

    }

    public ListSection(String... list) {
        this(Arrays.asList(list));
    }

    public ListSection(List<String> list) {
        Objects.requireNonNull(list, "List<String> mast not be null");  //задаем условие
        this.list = list;
    }

    public List<String> getList() {
        return list;
    }

    @Override
    public String toString() {
        return "ListSection{ list=" + list + "\n";
    }

    public String toListString() {
        String result = "";
        int size = list.size();
        for (int i = 0; i < size; i++) {
            if (i != size - 1) {
                result = result.concat(list.get(i) + "\n");
            } else {
                result = result.concat(list.get(i));
            }
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListSection that = (ListSection) o;

        return list.equals(that.list);
    }

    @Override
    public int hashCode() {
        return list.hashCode();
    }
}
