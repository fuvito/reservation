package com.shelby.reservation.controller;

import com.shelby.reservation.model.Flight;
import com.shelby.reservation.repo.FlightRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/flights")
public class ReservationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationController.class);

    @Autowired
    private FlightRepo flightRepo;

    @GetMapping(value = "/{id}")
    public Flight findById(@PathVariable("id") Integer id) {
        LOGGER.info("Get Flight with id " + id);
        return flightRepo.getFlight(id);
    }
}
