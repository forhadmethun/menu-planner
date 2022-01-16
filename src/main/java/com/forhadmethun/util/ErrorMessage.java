package com.forhadmethun.util;

public class ErrorMessage {

    public static final String INVALID_ARGUMENTS = "Invalid input format";
    public static final String INVALID_USER_TYPE = "Invalid user type";
    public static final String INVALID_DATE = "Invalid date";
    public static final String INVALID_DATE_RANGE = "Invalid date range";

    private ErrorMessage() {
        throw new IllegalStateException("Utility class");
    }
}
