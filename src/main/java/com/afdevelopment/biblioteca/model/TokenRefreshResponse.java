package com.afdevelopment.biblioteca.model;

public class TokenRefreshResponse {
    private String token;
    private String refreshToken;
    private String user;

    public TokenRefreshResponse(String token, String refreshToken, String user) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}