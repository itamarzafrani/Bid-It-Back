package com.dev.objects;

import javax.persistence.*;

@Entity
@Table(name = "my_users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column
    private String username;

    @Column
    private String token;

    @Column
    private double credits;

    @Column
    private boolean isAdmin;

    public User() {
    }

    public User(String username, String token,int credits,boolean isAdmin) {
        this.username = username;
        this.token = token;
        this.credits = credits;
        this.isAdmin = isAdmin;
    }

    public User(String username, String token) {
        this.username = username;
        this.token = token;
        this.credits = 1000;
        this.isAdmin = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public double getCredits() {
        return credits;
    }

    public void setCredits(double credits) {
        this.credits = credits;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
