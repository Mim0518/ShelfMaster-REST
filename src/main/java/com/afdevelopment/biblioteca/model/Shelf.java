package com.afdevelopment.biblioteca.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Shelf {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    private String location;

    public Shelf() {
    }

    public Integer getId() {
        return Id;
    }
    public void setId(Integer ID) {
        this.Id = ID;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Shelf{" +
                "ID=" + Id +
                ", location='" + location + '\'' +
                '}';
    }
}
