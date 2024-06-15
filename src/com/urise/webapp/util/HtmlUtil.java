package com.urise.webapp.util;

import com.urise.webapp.model.Organization;

public class HtmlUtil {
    public static String formatDates(Organization.Period period) {
        return String.format(DateUtil.format(period.getStartDate()) + " - " + DateUtil.format(period.getEndDate()));
    }

    public static boolean isEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }
}
