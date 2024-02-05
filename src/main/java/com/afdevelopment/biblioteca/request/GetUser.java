package com.afdevelopment.biblioteca.request;

public class GetUser {
    private Integer Id;
    private String curp;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }
}
