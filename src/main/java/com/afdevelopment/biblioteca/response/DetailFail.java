package com.afdevelopment.biblioteca.response;

import java.io.Serializable;


import java.util.ArrayList;
import java.util.List;

public class DetailFail implements Serializable {
    private List<Error> errors;

    public DetailFail() {
        this.errors = new ArrayList<>();
    }

    public List<Error> getErrors() {
        return errors;
    }

    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }
}
