package com.shelby.reservation.service;

import com.shelby.reservation.model.Flight;
import com.shelby.reservation.repo.FlightRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightService {
    private final FlightRepo flightRepo;

    public FlightService(@Autowired FlightRepo flightRepo) {
        this.flightRepo = flightRepo;
    }

    public Flight read(Long id) {
        return flightRepo.read(id);
    }

    public List<Flight> list() {
        return flightRepo.list();
    }
}
