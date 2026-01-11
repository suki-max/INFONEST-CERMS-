package com.example.demo.dto;

// Yeh file frontend se aa rahe JSON ko map karegi
// Jaise: { "email": "...", "password": "..." }
public class LoginRequest {

    private String email;
    private String password;

    // --- Getters and Setters ---
    // (Spring ko JSON data read karne ke liye inki zaroorat hai)
    
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}