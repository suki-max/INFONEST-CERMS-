package com.example.demo.dto;

public class RegisterRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role; 
    private String clubId; // Naya field jo missing tha

    // --- Getters and Setters ---
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    // Yeh methods controller ke liye zaroori hain
    public String getClubId() { return clubId; }
    public void setClubId(String clubId) { this.clubId = clubId; }
}