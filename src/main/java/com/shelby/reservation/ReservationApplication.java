package com.shelby.reservation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReservationApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationApplication.class);

    public static void main(String[] args) {
        LOGGER.info("Starting ReservationApplication");
        SpringApplication.run(ReservationApplication.class, args);
    }

}
