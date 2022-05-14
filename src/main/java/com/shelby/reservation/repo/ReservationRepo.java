package com.shelby.reservation.repo;

import com.shelby.reservation.error.DuplicateReservationException;
import com.shelby.reservation.error.RequiredFieldException;
import com.shelby.reservation.error.ReservationNotFoundException;
import com.shelby.reservation.model.Reservation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class ReservationRepo {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationRepo.class);

    private final Map<Long, Reservation> reservations; // reservation id - reservation

    public ReservationRepo() {
        LOGGER.info("Instantiating ReservationRepo");
        this.reservations = new HashMap<>();
    }

    public Reservation create(Reservation reservation) {
        validateFields(reservation);
        validateExistingReservation(reservation);

        // here generate id: use current time stamp
        Long newId = new Date().getTime();
        reservation.setId(newId);
        reservations.put(newId, reservation);

        return reservation;
    }

    public Reservation read(Long id) {
        LOGGER.info("Get Reservation with id " + id);
        if (!reservations.containsKey(id)) {
            LOGGER.error("Reservation with id " + id + " could not be found");
            throw new ReservationNotFoundException(id);
        }

        return reservations.get(id);
    }

    public Reservation update(Long id, Reservation reservation) {
        LOGGER.info("Update Reservation with id " + reservation.getId());

        validateFields(reservation);

        Reservation existingReservation = read(id);
        existingReservation.setFirstName(reservation.getFirstName()); // allow change only to First and Last names
        existingReservation.setLastName(reservation.getLastName());

        return reservation;
    }

    public void delete(Long id) {
        LOGGER.info("Delete Reservation with id " + id);
        if (!reservations.containsKey(id)) {
            LOGGER.error("Reservation with id " + id + " could not be found");
            throw new ReservationNotFoundException(id);
        }

        reservations.remove(id);
    }

    public List<Reservation> list() {
        return new ArrayList<>(reservations.values());
    }

    public List<Reservation> listByFlight(Long flightId) {
        return reservations
                .values()
                .stream()
                .filter(r -> r.getFlightId().equals(flightId)).collect(Collectors.toList());
    }

    public Reservation findByFlightIdAndEmail(Long flightId, String email) {
        return reservations.values()
                .stream()
                .filter(r -> r.getFlightId().equals(flightId) && r.getEmail().equals(email))
                .findAny().orElse(null);
    }

    private void validateFields(Reservation reservation) {
        // field validations
        {
            if (reservation.getFlightId() == null) {
                throw new RequiredFieldException("flightId", "null");
            }

            if (StringUtils.isEmpty(reservation.getEmail())) {
                throw new RequiredFieldException("email", reservation.getEmail());
            }

            if (StringUtils.isEmpty(reservation.getFirstName())) {
                throw new RequiredFieldException("firstName", reservation.getFirstName());
            }

            if (StringUtils.isEmpty(reservation.getLastName())) {
                throw new RequiredFieldException("lastName", reservation.getLastName());
            }
        }
    }

    private void validateExistingReservation(Reservation reservation) {

        // existing reservation
        {
            Reservation existingReservation = findByFlightIdAndEmail(reservation.getFlightId(), reservation.getEmail());
            if (existingReservation != null) {
                LOGGER.error(String.format("Flight with id [%s] has already reservation for [%s]", reservation.getFlightId(), reservation.getEmail()));
                throw new DuplicateReservationException(reservation.getFlightId(), reservation.getEmail());
            }
        }
    }
}
