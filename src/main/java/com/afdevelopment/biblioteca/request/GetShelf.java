package com.afdevelopment.biblioteca.request;

public class GetShelf {
    private Integer Id;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    @Override
    public String toString() {
        return "GetShelf{" +
                "Id=" + Id +
                '}';
    }
}
