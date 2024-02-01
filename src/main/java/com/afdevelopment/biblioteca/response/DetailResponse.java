package com.afdevelopment.biblioteca.response;

public class DetailResponse {
    private String code;
    private String bussinessMeaning;

    public DetailResponse() {
        this.code = "0";
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBussinessMeaning() {
        return bussinessMeaning;
    }

    public void setBussinessMeaning(String bussinessMeaning) {
        this.bussinessMeaning = bussinessMeaning;
    }
}
