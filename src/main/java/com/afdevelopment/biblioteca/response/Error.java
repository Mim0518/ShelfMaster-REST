package com.afdevelopment.biblioteca.response;

import java.io.Serializable;

public class Error implements Serializable {
    private String code;
    private String bussinessMeaning;

    public Error() {
        this.code = "-1";
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
