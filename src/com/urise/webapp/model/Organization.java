package com.urise.webapp.model;

import com.urise.webapp.util.DateUtil;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.urise.webapp.util.DateUtil.NOW;

public class Organization implements Serializable {
    protected String name;
    protected String webSite;
    protected List<Period> periods;

    public Organization(String name, String webSite, List<Period> periods) {
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

    public static class Period implements Serializable {
        private static final long serialVersionUID = 1L;
        protected LocalDate startDate;
        protected LocalDate endDate;
        protected String title;
        protected String description;

        public Period(int startYear, Month startMonth, String title, String description) {
            this(DateUtil.of(startYear, startMonth), NOW, title, description);
        }

        public Period(int startYear, Month startMonth, int endYear, Month endMonth, String title, String description) {
            this(DateUtil.of(startYear, startMonth), DateUtil.of(endYear, endMonth), title, description);
        }

        public Period(LocalDate startDate, LocalDate endDate, String title, String description) {
            Objects.requireNonNull(startDate, "startDate must not be null");
            Objects.requireNonNull(endDate, "endDate must not be null");
            Objects.requireNonNull(title, "title must not be null");
            this.startDate = startDate;
            this.endDate = endDate;
            this.title = title;
            this.description = description;
        }

//        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MM/yyyy");

        public LocalDate getStartDate() {
            return startDate;
        }

        public LocalDate getEndDate() {
            return endDate;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public String toString() {
//            return "Period{" + "startDate=" + "startDate.format(fmt)" + ", endDate=" + "endDate.format(fmt)" + "," +
//                    " title='" + title + '\'' + ", description='" + description + '}';
            return "Period{" + "startDate=" + startDate + ", endDate=" + endDate + "," +
                    " title='" + title + '\'' + ", description='" + description + '}';
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
//            return Objects.equals(fmt, period.fmt);
            return true;
        }

        @Override
        public int hashCode() {
            int result = startDate != null ? startDate.hashCode() : 0;
            result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
            result = 31 * result + (title != null ? title.hashCode() : 0);
            result = 31 * result + (description != null ? description.hashCode() : 0);
//            result = 31 * result + (fmt != null ? fmt.hashCode() : 0);
            return result;
        }
    }
}
