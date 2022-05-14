package com.shelby.reservation.controller;

import com.shelby.reservation.model.Flight;
import com.shelby.reservation.service.FlightService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/flights")
public class FlightController {
    private static final Logger LOGGER = LoggerFactory.getLogger(FlightController.class);

    private final FlightService flightService;

    public FlightController(@Autowired FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping()
    public ResponseEntity<List<Flight>> getFlights() {
        LOGGER.info("Get Flights");
        return new ResponseEntity<>(flightService.list(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Flight> getFlight(@PathVariable("id") Long id) {
        LOGGER.info("Get Flight with id " + id);
        return new ResponseEntity<>(flightService.read(id), HttpStatus.OK);
    }
}
