package com.shelby.reservation.model;

import java.util.HashSet;
import java.util.Set;

public class Flight {
    private Integer id;
    private Integer capacity;
    private Set<String> passengers; // only hold passenger names: names are assumed to be unique

    public Flight(int id, int capacity) {
        this.id = id;
        this.capacity = capacity;
        this.passengers = new HashSet<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Set<String> getPassengers() {
        return passengers;
    }

    public void setPassengers(Set<String> passengers) {
        this.passengers = passengers;
    }

    public void addPassenger(String passengerName) {
        if (this.passengers.contains(passengerName)) {
            // throw exception !!!
        }

        if (this.passengers.size() > this.capacity) {
            // throw exception
        }

        this.passengers.add(passengerName);
    }

    public void removePassenger(String passengerName) {
        if (!this.passengers.contains(passengerName)) {
            // throw exception !!!
        }

        this.passengers.remove(passengerName);
    }

    @Override
    public String toString() {
        return "Flight{" +
                "id=" + id +
                ", capacity=" + capacity +
                '}';
    }
}
