package com.example.demo.dto;

public class UserDTO {
    private Integer userId;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private String clubId;

    public UserDTO(Integer userId, String firstName, String lastName, String email, String role, String clubId) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
        this.clubId = clubId;
    }

    // --- Getters ---
    public Integer getUserId() { return userId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getRole() { return role; }
    public String getClubId() { return clubId; }
}