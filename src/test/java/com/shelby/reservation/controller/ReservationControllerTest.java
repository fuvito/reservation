package com.shelby.reservation.controller;

import com.shelby.reservation.error.DuplicateReservationException;
import com.shelby.reservation.error.FlightOverbookingException;
import com.shelby.reservation.model.Reservation;
import com.shelby.reservation.repo.ReservationRepo;
import com.shelby.reservation.repo.ReservationTestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReservationControllerTest {

    @Autowired
    private ReservationRepo reservationRepo;

    @Autowired
    private ReservationController reservationController;


    @Test
    void getReservations() {
        resetData();
        setupData(1L, 5, 1);
        setupData(2L, 6, 6);
        setupData(3L, 7, 12);
        setupData(4L, 8, 19);

        // test
        ResponseEntity<List<Reservation>> response = reservationController.getReservations();
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.hasBody());
        List<Reservation> list = response.getBody();
        assertNotNull(list);
        assertEquals(26, list.size());
    }

    @Test
    void getReservationsByFlight() {
        resetData();
        setupData(1L, 5, 1);
        setupData(2L, 6, 6);
        setupData(3L, 7, 12);
        setupData(4L, 8, 19);

        // test - 1
        {
            ResponseEntity<List<Reservation>> response = reservationController.getReservationsByFlight(3L);
            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertTrue(response.hasBody());
            List<Reservation> list = response.getBody();
            assertNotNull(list);
            assertEquals(7, list.size());
        }

        // test - 2
        {
            ResponseEntity<List<Reservation>> response = reservationController.getReservationsByFlight(4L);
            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertTrue(response.hasBody());
            List<Reservation> list = response.getBody();
            assertNotNull(list);
            assertEquals(8, list.size());
        }
    }

    @Test
    void getReservation() {
        resetData();
        setupData(1L, 1, 1);
        setupData(2L, 1, 2);
        setupData(3L, 1, 3);
        setupData(4L, 1, 4);

        // test - 1
        {
            Long id = getData(1L).get(0).getId();

            ResponseEntity<Reservation> response = reservationController.getReservation(id);
            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertTrue(response.hasBody());
            Reservation reservation = response.getBody();
            assertNotNull(reservation);
            assertEquals(id, reservation.getId());
        }
        // test - 2
        {
            Long id = getData(2L).get(0).getId();

            ResponseEntity<Reservation> response = reservationController.getReservation(id);
            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertTrue(response.hasBody());
            Reservation reservation = response.getBody();
            assertNotNull(reservation);
            assertEquals(id, reservation.getId());
        }

        // test - 3
        {
            Long id = getData(3L).get(0).getId();

            ResponseEntity<Reservation> response = reservationController.getReservation(id);
            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertTrue(response.hasBody());
            Reservation reservation = response.getBody();
            assertNotNull(reservation);
            assertEquals(id, reservation.getId());
        }

        // test - 4
        {
            Long id = getData(4L).get(0).getId();

            ResponseEntity<Reservation> response = reservationController.getReservation(id);
            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertTrue(response.hasBody());
            Reservation reservation = response.getBody();
            assertNotNull(reservation);
            assertEquals(id, reservation.getId());
        }
    }

    @Test
    void postReservation() {
        resetData();

        Reservation newReservation = new Reservation();
        newReservation.setFlightId(1L);
        newReservation.setEmail("new@domain.com");
        newReservation.setFirstName("NewFirst");
        newReservation.setLastName("NewLast");

        ResponseEntity<Reservation> response = reservationController.postReservation(newReservation);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.hasBody());

        Reservation createdReservation = response.getBody();
        assertNotNull(createdReservation);
        assertNotNull(createdReservation.getId());
        assertEquals(1L, createdReservation.getFlightId());
        assertEquals("new@domain.com", createdReservation.getEmail());
        assertEquals("NewFirst", createdReservation.getFirstName());
        assertEquals("NewLast", createdReservation.getLastName());
    }

    @Test
    void putReservation() {
        resetData();
        setupData(1L, 1, 1);
        Reservation existingReservation = getData(1L).get(0);

        Reservation editReservation = new Reservation();
        editReservation.setId(existingReservation.getId());
        editReservation.setFlightId(existingReservation.getFlightId());
        editReservation.setEmail(existingReservation.getEmail());
        editReservation.setFirstName("EditedFirst");
        editReservation.setLastName("EditedLast");

        ResponseEntity<Reservation> response = reservationController.putReservation(existingReservation.getId(), editReservation);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.hasBody());

        Reservation updatedReservation = response.getBody();
        assertNotNull(updatedReservation);
        assertNotNull(updatedReservation.getId());
        assertEquals(existingReservation.getId(), updatedReservation.getId());
        assertEquals(existingReservation.getFlightId(), updatedReservation.getFlightId());
        assertEquals(existingReservation.getEmail(), updatedReservation.getEmail());
        assertEquals("EditedFirst", updatedReservation.getFirstName());
        assertEquals("EditedLast", updatedReservation.getLastName());
    }

    @Test
    void deleteReservation() {
        resetData();
        setupData(1L, 1, 1);
        Reservation existingReservation = getData(1L).get(0);

        ResponseEntity<Reservation> response = reservationController.deleteReservation(existingReservation.getId());
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.hasBody());

        Reservation deletedReservation = response.getBody();
        assertNotNull(deletedReservation);
        assertNotNull(deletedReservation.getId());
        assertEquals(existingReservation.getId(), deletedReservation.getId());
        assertEquals(existingReservation.getFlightId(), deletedReservation.getFlightId());
        assertEquals(existingReservation.getEmail(), deletedReservation.getEmail());
        assertEquals(existingReservation.getFirstName(), deletedReservation.getFirstName());
        assertEquals(existingReservation.getLastName(), deletedReservation.getLastName());

        List<Reservation> reservationList = getData(1L);
        assertTrue(reservationList.isEmpty());
    }

    @Test
    void duplicateReservation() throws InterruptedException {
        resetData();

        Reservation resOriginal = new Reservation();
        resOriginal.setFlightId(1L);
        resOriginal.setEmail("dup@domain.com");
        resOriginal.setFirstName("DupFirst");
        resOriginal.setLastName("DupLast");

        ResponseEntity<Reservation> response = reservationController.postReservation(resOriginal);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.hasBody());

        Reservation resDuplicate = new Reservation();
        resDuplicate.setFlightId(1L);
        resDuplicate.setEmail("dup@domain.com"); // BE CAREFUL Passenger is identified by email
        resDuplicate.setFirstName("DupFirst2");
        resDuplicate.setLastName("DupLast2");

        // try duplicate : same flight with same email address
        try {
            Thread.sleep(10); // give some time between posts: reservations may get some id
            reservationController.postReservation(resDuplicate);
            fail("Duplicate Reservation created");
        } catch (DuplicateReservationException e) {
            assertEquals(DuplicateReservationException.generateMessage(resOriginal.getFlightId(), resOriginal.getEmail()), e.getMessage());
        } catch (Exception e) {
            fail("No Other Exception is Expected");
        }

        // NOT duplicate : different flight with same email address
        Reservation resNotDuplicate1 = new Reservation();
        resNotDuplicate1.setFlightId(2L);
        resNotDuplicate1.setEmail("dup@domain.com");
        resNotDuplicate1.setFirstName("DupFirst");
        resNotDuplicate1.setLastName("DupLast");

        Thread.sleep(10); // give some time between posts: reservations may get some id
        response = reservationController.postReservation(resNotDuplicate1);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.hasBody());

        // NOT duplicate : same flight with different email address
        Thread.sleep(10); // give some time between posts: reservations may get some id
        Reservation resNotDuplicate2 = new Reservation();
        resNotDuplicate2.setFlightId(1L);
        resNotDuplicate2.setEmail("dup2@domain.com");
        resNotDuplicate2.setFirstName("DupFirst");
        resNotDuplicate2.setLastName("DupLast");

        Thread.sleep(10); // give some time between posts: reservations may get some id
        response = reservationController.postReservation(resNotDuplicate2);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.hasBody());

        // now Flight 1 has 2 reservations, Flight 2 has 1 reservation
        assertEquals(2, getData(1L).size());
        assertEquals(1, getData(2L).size());
    }

    @Test
    void overbookingReservation() throws InterruptedException {
        resetData();
        setupData(1L, 10, 1); // Flight 1 has Capacity 10

        Reservation overbookingReservation = new Reservation();
        overbookingReservation.setFlightId(1L);
        overbookingReservation.setEmail("Overbooking@domain.com");
        overbookingReservation.setFirstName("OverbookingFirst");
        overbookingReservation.setLastName("OverbookingLast");

        // try to create new Reservation
        try {
            Thread.sleep(10); // give some time between posts: reservations may get some id
            reservationController.postReservation(overbookingReservation);
            fail("Overbooked Reservation created");
        } catch (FlightOverbookingException e) {
            assertEquals(FlightOverbookingException.generateMessage(1L, 10), e.getMessage());
        } catch (Exception e) {
            fail("No Other Exception is Expected");
        }

        // now delete one reservation
        reservationController.deleteReservation(getData(1L).get(0).getId());

        Thread.sleep(10); // give some time between posts: reservations may get some id
        ResponseEntity<Reservation> response = reservationController.postReservation(overbookingReservation);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.hasBody());

        // now Flight 1 has 10
        assertEquals(10, getData(1L).size());
    }


    private void resetData() {
        ReservationTestUtil.resetReservations(reservationRepo);
    }

    private void setupData(long flightNo, int numberOfReservations, int startNo) {
        ReservationTestUtil.createTestReservations(reservationRepo, flightNo, numberOfReservations, startNo);
    }

    private List<Reservation> getData(long flightNo) {
        return ReservationTestUtil.getTestReservations(reservationRepo, flightNo);
    }
}