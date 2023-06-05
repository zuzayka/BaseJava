package com.urise.webapp.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Period period = (Period) o;

        if (!Objects.equals(startDate, period.startDate)) return false;
        if (!Objects.equals(endDate, period.endDate)) return false;
        if (!Objects.equals(title, period.title)) return false;
        if (!Objects.equals(description, period.description)) return false;
        return Objects.equals(fmt, period.fmt);
    }

    @Override
    public int hashCode() {
        int result = startDate != null ? startDate.hashCode() : 0;
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (fmt != null ? fmt.hashCode() : 0);
        return result;
    }
}
