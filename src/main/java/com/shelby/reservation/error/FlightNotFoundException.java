package com.shelby.reservation.error;

public class FlightNotFoundException extends RuntimeException {
    private static final String ERR_MESSAGE = "Flight with id [%s] could not be found";

    public FlightNotFoundException(Long id) {
        super(generateMessage(id));
    }

    public static String generateMessage(Long id) {
        return String.format(ERR_MESSAGE, id);
    }
}
