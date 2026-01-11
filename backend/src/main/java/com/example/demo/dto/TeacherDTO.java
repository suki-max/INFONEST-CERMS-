package com.example.demo.dto;

// Yeh ek Data Transfer Object (DTO) hai.
// Yeh User + Teacher table se combined data rakhega
public class TeacherDTO {
    private Integer userId;
    private String firstName;
    private String lastName;
    private String email;
    private String schedulePdfPath;
    private String sittingCabin;

    // Constructor
    public TeacherDTO(Integer userId, String firstName, String lastName, String email, String schedulePdfPath, String sittingCabin) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.schedulePdfPath = schedulePdfPath;
        this.sittingCabin = sittingCabin;
    }

    // --- Sirf Getters ---
    public Integer getUserId() { return userId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getSchedulePdfPath() { return schedulePdfPath; }
    public String getSittingCabin() { return sittingCabin; }
}