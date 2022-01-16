package com.forhadmethun.util;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class DateUtil {

    private static final String FORMAT = "yyyy-MM-dd";

    private DateUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static boolean isValidDate(String dateStr) {
        var sdf = new SimpleDateFormat(FORMAT);
        sdf.setLenient(false);
        return sdf.parse(dateStr, new ParsePosition(0)) != null;
    }

    public static boolean isWithinRange(LocalDate testDate, LocalDate startDate, LocalDate endDate) {
        return !(testDate.isBefore(startDate) || testDate.isAfter(endDate));
    }
}
