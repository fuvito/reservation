package com.shelby.reservation.service;

import com.shelby.reservation.error.FlightOverbookingException;
import com.shelby.reservation.model.Flight;
import com.shelby.reservation.model.Reservation;
import com.shelby.reservation.repo.FlightRepo;
import com.shelby.reservation.repo.ReservationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepo reservationRepo;

    private final FlightRepo flightRepo;

    public ReservationService(@Autowired ReservationRepo reservationRepo, @Autowired FlightRepo flightRepo) {
        this.reservationRepo = reservationRepo;
        this.flightRepo = flightRepo;
    }

    public Reservation create(Reservation reservation) {
        // check flight
        Flight flight = flightRepo.read(reservation.getFlightId());

        // check flight Capacity for overbooking
        int flightCapacity = flight.getCapacity();
        List<Reservation> flightReservations = reservationRepo.listByFlight(flight.getId());

        if (flightCapacity <= flightReservations.size()) {
            throw new FlightOverbookingException(flight.getId(), flightCapacity);
        }

        return reservationRepo.create(reservation);
    }

    public Reservation read(Long id) {
        return reservationRepo.read(id);
    }

    public Reservation update(Long id, Reservation reservation) {
        return reservationRepo.update(id, reservation);
    }

    public Reservation delete(Long id) {
        Reservation deletedReservation = read(id);
        reservationRepo.delete(id);
        return deletedReservation;
    }

    public List<Reservation> list() {
        List<Reservation> list = reservationRepo.list();
        list.sort(Comparator.comparing(Reservation::getId));
        return list;
    }

    public List<Reservation> listByFlight(Long flightId) {
        List<Reservation> list = reservationRepo.listByFlight(flightId);
        list.sort(Comparator.comparing(Reservation::getId));
        return list;
    }
}
