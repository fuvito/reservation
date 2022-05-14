package com.shelby.reservation.controller;

import com.shelby.reservation.model.Reservation;
import com.shelby.reservation.service.ReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationController.class);

    private final ReservationService reservationService;

    public ReservationController(@Autowired ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    /**
     * List all Reservations
     *
     * @return List of All Reservations for all Flights
     */
    @GetMapping()
    public ResponseEntity<List<Reservation>> getReservations() {
        LOGGER.info("Get Reservations");
        return new ResponseEntity<>(reservationService.list(), HttpStatus.OK);
    }

    /**
     * List all Reservations for a Flight
     *
     * @return List of All Reservations for a Flight
     */
    @GetMapping(value = "/byflight/{flightId}")
    public ResponseEntity<List<Reservation>> getReservationsByFlight(@PathVariable("flightId") Long flightId) {
        LOGGER.info("Get Reservations for Flight  " + flightId);
        return new ResponseEntity<>(reservationService.listByFlight(flightId), HttpStatus.OK);
    }

    /**
     * Get a Reservation by its id
     *
     * @param id Reservation Id
     * @return found Reservation
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<Reservation> getReservation(@PathVariable("id") Long id) {
        LOGGER.info("Get Reservation with id " + id);
        return new ResponseEntity<>(reservationService.read(id), HttpStatus.OK);
    }

    /**
     * Add / Create Reservation
     *
     * @param reservation reservation to be created
     * @return created reservation
     */
    @PostMapping(value = "")
    public ResponseEntity<Reservation> postReservation(@RequestBody Reservation reservation) {
        LOGGER.info("Post Reservation " + reservation);
        return new ResponseEntity<>(reservationService.create(reservation), HttpStatus.OK);
    }

    /**
     * Update Reservation
     *
     * @param id          reservation to be updated
     * @param reservation reservation to be updated
     * @return updated reservation
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<Reservation> putReservation(@PathVariable("id") Long id, @RequestBody Reservation reservation) {
        LOGGER.info("Put Reservation id: " + id + " with data " + reservation);
        return new ResponseEntity<>(reservationService.update(id, reservation), HttpStatus.OK);
    }

    /**
     * Delete Reservation
     *
     * @param id id of the reservation to be deleted
     * @return deleted reservation
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Reservation> deleteReservation(@PathVariable("id") Long id) {
        LOGGER.info("Delete Reservation id: " + id);
        return new ResponseEntity<>(reservationService.delete(id), HttpStatus.OK);
    }
}
