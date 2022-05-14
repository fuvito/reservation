package com.shelby.reservation.error;

public class DuplicateReservationException extends RuntimeException {
    private static final String ERR_MESSAGE = "Flight with id [%s] has already reservation for [%s]";

    public DuplicateReservationException(Long id, String email) {
        super(generateMessage(id, email));
    }

    public static String generateMessage(Long id, String email) {
        return String.format(ERR_MESSAGE, id, email);
    }
}
