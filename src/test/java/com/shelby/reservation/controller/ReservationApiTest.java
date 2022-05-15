package com.shelby.reservation.controller;

import com.shelby.reservation.model.Reservation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReservationApiTest extends ReservationTestBase {
    private static final String RESERVATIONS_API = "/api/v1/reservations";

    @Test
    void getReservations(@Autowired TestRestTemplate restTemplate) {
        resetData();
        setupData(1L, 5, 1);
        setupData(2L, 6, 6);
        setupData(3L, 7, 12);
        setupData(4L, 8, 19);

        ResponseEntity<Reservation[]> response = restTemplate.getForEntity(RESERVATIONS_API, Reservation[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Reservation[] list = response.getBody();
        assertNotNull(list);
        assertEquals(26, list.length);
    }

    @Test
    void getReservationsByFlight(@Autowired TestRestTemplate restTemplate) {
        resetData();
        setupData(1L, 5, 1);
        setupData(2L, 6, 6);
        setupData(3L, 7, 12);
        setupData(4L, 8, 19);

        // test - 1
        {
            String url = RESERVATIONS_API + "/byflight/" + 1;
            ResponseEntity<Reservation[]> response = restTemplate.getForEntity(url, Reservation[].class);
            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertTrue(response.hasBody());
            Reservation[] list = response.getBody();
            assertNotNull(list);
            assertEquals(5, list.length);
        }

        // test - 2
        {
            String url = RESERVATIONS_API + "/byflight/" + 2;
            ResponseEntity<Reservation[]> response = restTemplate.getForEntity(url, Reservation[].class);
            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertTrue(response.hasBody());
            Reservation[] list = response.getBody();
            assertNotNull(list);
            assertEquals(6, list.length);
        }
    }

    @Test
    void getReservation(@Autowired TestRestTemplate restTemplate) {
        resetData();
        setupData(1L, 1, 1);
        setupData(2L, 1, 2);
        setupData(3L, 1, 3);
        setupData(4L, 1, 4);

        // test - 1
        {
            Long id = getData(1L).get(0).getId();

            String url = RESERVATIONS_API + "/" + id;
            ResponseEntity<Reservation> response = restTemplate.getForEntity(url, Reservation.class);
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

            String url = RESERVATIONS_API + "/" + id;
            ResponseEntity<Reservation> response = restTemplate.getForEntity(url, Reservation.class);
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

            String url = RESERVATIONS_API + "/" + id;
            ResponseEntity<Reservation> response = restTemplate.getForEntity(url, Reservation.class);
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

            String url = RESERVATIONS_API + "/" + id;
            ResponseEntity<Reservation> response = restTemplate.getForEntity(url, Reservation.class);
            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertTrue(response.hasBody());
            Reservation reservation = response.getBody();
            assertNotNull(reservation);
            assertEquals(id, reservation.getId());
        }
    }

    @Test
    void postReservation(@Autowired TestRestTemplate restTemplate) {
        resetData();

        Reservation newReservation = new Reservation();
        newReservation.setFlightId(1L);
        newReservation.setEmail("new@domain.com");
        newReservation.setFirstName("NewFirst");
        newReservation.setLastName("NewLast");

        ResponseEntity<Reservation> response = restTemplate.postForEntity(RESERVATIONS_API, newReservation, Reservation.class);
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
    void putReservation(@Autowired TestRestTemplate restTemplate) {
        resetData();
        setupData(1L, 1, 1);
        Reservation existingReservation = getData(1L).get(0);

        Reservation editReservation = new Reservation();
        editReservation.setId(existingReservation.getId());
        editReservation.setFlightId(existingReservation.getFlightId());
        editReservation.setEmail(existingReservation.getEmail());
        editReservation.setFirstName("EditedFirst");
        editReservation.setLastName("EditedLast");

        String url = RESERVATIONS_API + "/" + existingReservation.getId();
        HttpEntity<Reservation> requestEntity = new HttpEntity<>(editReservation);
        ResponseEntity<Reservation> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Reservation.class);

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
    void deleteReservation(@Autowired TestRestTemplate restTemplate) {
        resetData();
        setupData(1L, 1, 1);
        Reservation existingReservation = getData(1L).get(0);

        String url = RESERVATIONS_API + "/" + existingReservation.getId();
        ResponseEntity<Reservation> response = restTemplate.exchange(url, HttpMethod.DELETE, null, Reservation.class);
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
    void duplicateReservation(@Autowired TestRestTemplate restTemplate) throws InterruptedException {
        resetData();

        Reservation resOriginal = new Reservation();
        resOriginal.setFlightId(1L);
        resOriginal.setEmail("dup@domain.com");
        resOriginal.setFirstName("DupFirst");
        resOriginal.setLastName("DupLast");

        String url = RESERVATIONS_API;
        ResponseEntity<Reservation> response = restTemplate.postForEntity(url, resOriginal, Reservation.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.hasBody());

        Reservation resDuplicate = new Reservation();
        resDuplicate.setFlightId(1L);
        resDuplicate.setEmail("dup@domain.com"); // BE CAREFUL Passenger is identified by email
        resDuplicate.setFirstName("DupFirst2");
        resDuplicate.setLastName("DupLast2");

        // try duplicate : same flight with same email address
        Thread.sleep(10); // give some time between posts: reservations may get some id
        response = restTemplate.postForEntity(url, resDuplicate, Reservation.class);
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.hasBody());


        // NOT duplicate : different flight with same email address
        Reservation resNotDuplicate1 = new Reservation();
        resNotDuplicate1.setFlightId(2L);
        resNotDuplicate1.setEmail("dup@domain.com");
        resNotDuplicate1.setFirstName("DupFirst");
        resNotDuplicate1.setLastName("DupLast");

        Thread.sleep(10); // give some time between posts: reservations may get some id
        response = restTemplate.postForEntity(url, resNotDuplicate1, Reservation.class);
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
        response = restTemplate.postForEntity(url, resNotDuplicate2, Reservation.class);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.hasBody());

        // now Flight 1 has 2 reservations, Flight 2 has 1 reservation
        assertEquals(2, getData(1L).size());
        assertEquals(1, getData(2L).size());
    }

    @Test
    void overbookingReservation(@Autowired TestRestTemplate restTemplate) throws InterruptedException {
        resetData();
        setupData(1L, 10, 1); // Flight 1 has Capacity 10

        Reservation overbookingReservation = new Reservation();
        overbookingReservation.setFlightId(1L);
        overbookingReservation.setEmail("Overbooking@domain.com");
        overbookingReservation.setFirstName("OverbookingFirst");
        overbookingReservation.setLastName("OverbookingLast");

        String url = RESERVATIONS_API;
        ResponseEntity<Reservation> response;

        // try to create new Reservation
        Thread.sleep(10); // give some time between posts: reservations may get some id
        response = restTemplate.postForEntity(url, overbookingReservation, Reservation.class);
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.hasBody());

        // now delete one reservation

        String urlDelete = RESERVATIONS_API + "/" + getData(1L).get(0).getId();
        restTemplate.exchange(urlDelete, HttpMethod.DELETE, null, Reservation.class);

        Thread.sleep(10); // give some time between posts: reservations may get some id
        response = restTemplate.postForEntity(url, overbookingReservation, Reservation.class);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.hasBody());

        // now Flight 1 has 10
        assertEquals(10, getData(1L).size());
    }
}
