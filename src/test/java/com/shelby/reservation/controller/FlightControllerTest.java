package com.shelby.reservation.controller;

import com.shelby.reservation.error.FlightNotFoundException;
import com.shelby.reservation.model.Flight;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FlightControllerTest {

    @Autowired
    private FlightController flightController;

    @Test
    void getFlights() {
        ResponseEntity<List<Flight>> flightListResponse = flightController.getFlights();
        assertNotNull(flightListResponse);
        assertEquals(HttpStatus.OK, flightListResponse.getStatusCode());
        assertTrue(flightListResponse.hasBody());

        List<Flight> flightList = flightListResponse.getBody();
        assertNotNull(flightList);
        assertEquals(4, flightList.size());
        assertEquals(1, flightList.get(0).getId());
        assertEquals(4, flightList.get(3).getId());
    }

    @Test
    void getFlightValid1() {
        long id = 1;
        HttpStatus expectedStatus = HttpStatus.OK;
        int expectedCapacity = 10;

        runAndAssertGetFlight(id, expectedStatus, expectedCapacity);
    }

    @Test
    void getFlightValid2() {
        long id = 2;
        HttpStatus expectedStatus = HttpStatus.OK;
        int expectedCapacity = 20;

        runAndAssertGetFlight(id, expectedStatus, expectedCapacity);
    }

    @Test
    void getFlightInvalid1() {
        long id = 5;
        try {
            flightController.getFlight(id);
            fail("getFlight should throw FlightNotFoundException exception");
        } catch (Exception e) {
            assertTrue(e instanceof FlightNotFoundException);
            assertEquals(FlightNotFoundException.generateMessage(id), e.getMessage());
        }
    }

    private void runAndAssertGetFlight(long id, HttpStatus expectedStatus, int expectedCapacity) {
        ResponseEntity<Flight> flightResponse = flightController.getFlight(id);
        assertNotNull(flightResponse);
        assertEquals(expectedStatus, flightResponse.getStatusCode());
        assertTrue(flightResponse.hasBody());

        Flight flight = flightResponse.getBody();
        assertNotNull(flight);
        assertEquals(id, flight.getId());
        assertEquals(expectedCapacity, flight.getCapacity());
    }
}