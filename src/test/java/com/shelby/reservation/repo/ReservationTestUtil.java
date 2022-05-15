package com.shelby.reservation.repo;

import com.shelby.reservation.model.Reservation;

import java.util.List;

import static java.lang.Thread.sleep;

public class ReservationTestUtil {
    public static void resetReservations(ReservationRepo repo) {
        List<Reservation> reservationList = repo.list();
        reservationList.forEach(r -> repo.delete(r.getId()));
    }

    public static void createTestReservations(ReservationRepo repo, Long flightId, int numberOfReservations, int startNo) {
        for (int i = startNo; i < numberOfReservations + startNo; i++) {
            String testEmail = String.format("test_%s@domain.com", i);
            String testFirstName = String.format("First_%s", i);
            String testLastName = String.format("Last_%s", i);
            Reservation r = new Reservation();
            r.setFlightId(flightId);
            r.setEmail(testEmail);
            r.setFirstName(testFirstName);
            r.setLastName(testLastName);
            repo.create(r);
            try {
                sleep(10);
            } catch (InterruptedException e) {
                // skip exception
            }
        }
    }

    public static List<Reservation> getTestReservations(ReservationRepo reservationRepo, long flightNo) {
        return reservationRepo.listByFlight(flightNo);
    }
}
