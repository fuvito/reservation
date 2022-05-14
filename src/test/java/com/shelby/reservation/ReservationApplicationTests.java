package com.shelby.reservation;

import com.shelby.reservation.controller.FlightController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ReservationApplicationTests {

    @Autowired
    private FlightController controller;

    @Test
    void contextLoads() {
        assertNotNull(controller);
    }

}
