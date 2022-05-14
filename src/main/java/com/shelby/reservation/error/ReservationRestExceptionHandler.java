package com.shelby.reservation.error;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ReservationRestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({
            DuplicateReservationException.class,
            FlightNotFoundException.class,
            FlightOverbookingException.class,
            RequiredFieldException.class,
            ReservationNotFoundException.class,
    })
    public ResponseEntity<Object> handleConstraintViolation(RuntimeException ex) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

}
