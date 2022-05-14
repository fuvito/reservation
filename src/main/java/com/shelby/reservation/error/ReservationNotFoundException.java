package com.shelby.reservation.error;

public class ReservationNotFoundException extends RuntimeException {
    private static final String ERR_MESSAGE = "Reservation with id [%s] could not be found";

    public ReservationNotFoundException(Long id) {
        super(generateMessage(id));
    }

    public static String generateMessage(Long id) {
        return String.format(ERR_MESSAGE, id);
    }
}
