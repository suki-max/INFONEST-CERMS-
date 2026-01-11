package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "teacher")
public class Teacher {

    @Id
    @Column(name = "user_id")
    private Integer userId; // Yeh Primary Key hai

    @Column(name = "schedule_pdf_path")
    private String schedulePdfPath;

    @Column(name = "sitting_cabin")
    private String sittingCabin;

    // --- Relationship ---
    // Yeh Spring ko batata hai ki is table ka user_id
    // User entity se directly linked hai.
    @OneToOne
    @MapsId // Yeh batata hai ki user_id column hi PK aur FK dono hai
    @JoinColumn(name = "user_id")
    private User user;

    // --- Getters and Setters ---

    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public String getSchedulePdfPath() {
        return schedulePdfPath;
    }
    public void setSchedulePdfPath(String schedulePdfPath) {
        this.schedulePdfPath = schedulePdfPath;
    }
    public String getSittingCabin() {
        return sittingCabin;
    }
    public void setSittingCabin(String sittingCabin) {
        this.sittingCabin = sittingCabin;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
}