package com.freshlybrewed.raterecomm.entities;




import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;

@Entity
@Table(name = "users") // Optional: Define the table name if needed
public class User {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long userId;

    private String username;

    // Constructors, getters, setters, and other methods

    public User() {
        // Default constructor
    }

    public User(String username) {
        this.username = username;
    }

    // Getter and setter methods for id and username

    public Long getsUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
