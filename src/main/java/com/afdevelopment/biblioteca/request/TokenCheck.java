package com.afdevelopment.biblioteca.request;

public class TokenCheck {
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
                "token='" + token + '\'' +
                '}';
    }
}
