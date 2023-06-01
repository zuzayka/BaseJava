package com.urise.webapp.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Period {
    protected LocalDate startDate;
    protected LocalDate endDate;
    protected String title;
    protected String description;

    public Period(LocalDate startDate, LocalDate endDate,
                  String title, String description) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.description = description;
    }

    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MM/yyyy");

    @Override
    public String toString() {
        return "Period{" + "startDate=" + startDate.format(fmt) + ", endDate=" + endDate.format(fmt)
                + ", title='" + title + '\'' + ", description='" + description + '}';
    }
}
