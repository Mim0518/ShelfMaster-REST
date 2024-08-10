package com.afdevelopment.biblioteca.model;

public class AuthResponse {
    String token;
    String user;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "AuthResponse{" +
                "token='" + token + '\'' +
                ", user='" + user + '\'' +
                '}';
    }
}
