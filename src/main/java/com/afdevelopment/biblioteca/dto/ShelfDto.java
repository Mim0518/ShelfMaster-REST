package com.afdevelopment.biblioteca.dto;

public class ShelfDto {
    private Integer Id;
    private String location;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "ShelfDto{" +
                "Id=" + Id +
                ", location='" + location + '\'' +
                '}';
    }
}
