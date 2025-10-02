package com.afdevelopment.biblioteca.request;

import jakarta.validation.constraints.NotBlank;

public class TokenCheck {
    @NotBlank(message = "Token is required")
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "TokenCheck{" +
                "token='***'" +
                '}';
    }
}
