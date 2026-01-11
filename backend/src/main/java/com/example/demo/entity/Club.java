package com.example.demo.entity;

// --- ZAROORI IMPORTS (Inhein check kijiye) ---
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id; // <-- Yeh sabse important import hai
import jakarta.persistence.Table;

// @Entity batata hai ki yeh ek database table se mapped hai
@Entity
// @Table batata hai ki table ka naam "club" hai
@Table(name = "club")
public class Club {

    // @Id batata hai ki yeh Primary Key hai
    @Id
    @Column(name = "club_id")
    private String clubId;

    @Column(name = "name")
    private String name;

    // --- Constructors ---
    // Ek default constructor zaroori hai
    public Club() {
    }

    // --- Getters and Setters ---
    // Spring/JPA ko in methods ki zaroorat hoti hai data read/write karne ke liye

    public String getClubId() {
        return clubId;
    }

    public void setClubId(String clubId) {
        this.clubId = clubId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}