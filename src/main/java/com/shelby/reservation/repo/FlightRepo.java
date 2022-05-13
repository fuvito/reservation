package com.shelby.reservation.repo;

import com.shelby.reservation.model.Flight;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class FlightRepo {
    private static final Logger LOGGER = LoggerFactory.getLogger(FlightRepo.class);

    private Map<Integer, Flight> flights;

    public FlightRepo() {
        this.flights = new HashMap<>();
        addFlight(1, 10); // Flight 1 with capacity 10
        addFlight(2, 20); // Flight 2 with capacity 20
        addFlight(3, 30); // Flight 3 with capacity 30
        addFlight(4, 40); // Flight 4 with capacity 40
    }

    private void addFlight(int id, int capacity) {
        Flight flight = new Flight(id, capacity);
        this.flights.put(id, flight);
    }

    public Flight getFlight(int id) {
        LOGGER.info("Querying Flight with id " + id);
        if (!flights.containsKey(id)) {
            LOGGER.error("Flight with id " + id + " could not be found");
            // throw Exception
        }

        return flights.get(id);
    }

    public List<Flight> getFlights() {
        return flights.values()
                .stream()
                .sorted(Comparator.comparing(Flight::getId))
                .collect(Collectors.toList());
    }
}
