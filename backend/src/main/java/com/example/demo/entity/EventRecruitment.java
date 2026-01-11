package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "event_recruitment") // Humne table rename kiya tha
public class EventRecruitment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "registration_id")
    private Integer registrationId;

    // JSON ko hum String ki tarah treat karenge,
    // lekin database ko batayenge ki yeh JSON hai.
    // Isse PostgreSQL ka native JSON type istemaal hoga.
    @Column(name = "form_data", columnDefinition = "json")
    private String formData;

    @Column(name = "status")
    private String status;

    @Column(name = "submission_date")
    private LocalDateTime submissionDate;

    // --- Relationships (JOINs ke liye) ---

    // Har Application ek Event se judi hai
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "event_id") // Yeh 'event_id' column ko join karega
    private Event event;

    // Har Application ek User (Student) se judi hai
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id") // Yeh 'user_id' column (jo humne rename kiya tha) ko join karega
    private User user;


    // --- Getters and Setters ---

    public Integer getRegistrationId() { return registrationId; }
    public void setRegistrationId(Integer registrationId) { this.registrationId = registrationId; }
    public String getFormData() { return formData; }
    public void setFormData(String formData) { this.formData = formData; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getSubmissionDate() { return submissionDate; }
    public void setSubmissionDate(LocalDateTime submissionDate) { this.submissionDate = submissionDate; }
    public Event getEvent() { return event; }
    public void setEvent(Event event) { this.event = event; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}