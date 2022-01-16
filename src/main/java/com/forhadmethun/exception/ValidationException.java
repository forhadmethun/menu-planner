package com.forhadmethun.exception;

public class ValidationException extends Exception {
    public ValidationException(String message) {
        super(message);
    }

    public static void isNotNull(Object data, String message) throws ValidationException {
        if (data == null) throw new ValidationException(message);
    }

    public static void isTrue(boolean data, String message) throws ValidationException {
        if (!data) throw new ValidationException(message);
    }
}
