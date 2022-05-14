package com.shelby.reservation.error;

public class FlightOverbookingException extends RuntimeException {
    private static final String ERR_MESSAGE = "Flight with id [%s] is already at capacity [%s], No more reservations can be created!";

    public FlightOverbookingException(Long id, Integer capacity) {
        super(generateMessage(id, capacity));
    }

    public static String generateMessage(Long id, Integer capacity) {
        return String.format(ERR_MESSAGE, id, capacity);
    }
}
