package com.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Player")
public class Player {

    @Id
    private int id;
    private String username;
    private String email;
    private String passHash;

    public Player() {
    }

    public Player(String username, String email, String passHash) {
        this.username = username;
        this.email = email;
        this.passHash = passHash;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
