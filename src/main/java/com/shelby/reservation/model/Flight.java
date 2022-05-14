package com.shelby.reservation.model;

public class Flight {
    private Long id;
    private Integer capacity;

    public Flight() {
    }

    public Flight(Long id, int capacity) {
        this.id = id;
        this.capacity = capacity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }


    @Override
    public String toString() {
        return "Flight{" +
                "id=" + id +
                ", capacity=" + capacity +
                '}';
    }
}
