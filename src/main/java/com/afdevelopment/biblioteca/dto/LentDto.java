package com.afdevelopment.biblioteca.dto;

public class LentDto {
    private Integer id;
    private Integer user_id;
    private String date;
    private boolean lent;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isLent() {
        return lent;
    }

    public void setLent(boolean lent) {
        this.lent = lent;
    }

    @Override
    public String toString() {
        return "LentDto{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", date='" + date + '\'' +
                ", lent=" + lent +
                '}';
    }
}
