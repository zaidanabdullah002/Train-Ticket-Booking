package org.entities;

public class User {

    public String userId;
    public String username;
    public String passwordHash;

    // REQUIRED for Jackson
    public User() {
    }

    // Optional convenience constructor (not used by Jackson)
    public User(String userId, String username, String passwordHash) {
        this.userId = userId;
        this.username = username;
        this.passwordHash = passwordHash;
    }
}
