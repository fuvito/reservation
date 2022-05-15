package com.shelby.reservation.controller;

import com.shelby.reservation.model.Reservation;
import com.shelby.reservation.repo.ReservationRepo;
import com.shelby.reservation.repo.ReservationTestUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class ReservationTestBase {

    @Autowired
    private ReservationRepo reservationRepo;

    protected void resetData() {
        ReservationTestUtil.resetReservations(reservationRepo);
    }

    protected void setupData(long flightNo, int numberOfReservations, int startNo) {
        ReservationTestUtil.createTestReservations(reservationRepo, flightNo, numberOfReservations, startNo);
    }

    protected List<Reservation> getData(long flightNo) {
        return ReservationTestUtil.getTestReservations(reservationRepo, flightNo);
    }
}
