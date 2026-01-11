package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Integer eventId;

    @Column(name = "club_id")
    private String clubId;

    @Column(name = "venue_id")
    private Integer venueId;

    @Column(name = "deadline")
    private LocalDate deadline;

    @Column(name = "time")
    private String time;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "name")
    private String name;

    @Column(name = "registration_form_link")
    private String registrationFormLink;

    // --- NAYA FIELD ADD KIYA ---
    @Column(name = "is_hidden")
    private boolean isHidden = false; 

    // --- Getters and Setters ---

    public Integer getEventId() { return eventId; }
    public void setEventId(Integer eventId) { this.eventId = eventId; }

    public String getClubId() { return clubId; }
    public void setClubId(String clubId) { this.clubId = clubId; }

    public Integer getVenueId() { return venueId; }
    public void setVenueId(Integer venueId) { this.venueId = venueId; }

    public LocalDate getDeadline() { return deadline; }
    public void setDeadline(LocalDate deadline) { this.deadline = deadline; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getRegistrationFormLink() { return registrationFormLink; }
    public void setRegistrationFormLink(String registrationFormLink) { this.registrationFormLink = registrationFormLink; }

    public boolean isHidden() { return isHidden; }
    public void setHidden(boolean hidden) { isHidden = hidden; }
}