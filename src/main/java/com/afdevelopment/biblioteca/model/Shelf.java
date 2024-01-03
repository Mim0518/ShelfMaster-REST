package com.afdevelopment.biblioteca.model;

public class Shelf {
    private Integer ID;
    private String location;

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
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
                "ID=" + ID +
                ", location='" + location + '\'' +
                '}';
    }
}
