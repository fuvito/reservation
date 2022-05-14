package com.shelby.reservation.error;

public class RequiredFieldException extends RuntimeException {
    private static final String ERR_MESSAGE = "Field [%s] is required, but value provided [%s]";

    public RequiredFieldException(String fieldName, String fieldValue) {
        super(generateMessage(fieldName, fieldValue));
    }

    public static String generateMessage(String fieldName, String fieldValue) {
        return String.format(ERR_MESSAGE, fieldName, fieldValue);
    }
}
