package com.example.demo.dto;

import java.time.LocalDateTime;

// Yeh DTO combined (joined) data ko frontend par bhejega
public class EventRecruitmentDTO {

    private Integer registrationId;
    private String formData;
    private String status;
    private LocalDateTime submissionDate;

    // Joined Data from Event and User tables
    private String eventName;
    private String studentName;
    private String studentEmail;

    // 1. Empty Constructor (Spring Bean/Mapping ke liye zaroori hai)
    public EventRecruitmentDTO() {}

    // 2. Full Constructor
    public EventRecruitmentDTO(Integer registrationId, String formData, String status, LocalDateTime submissionDate,
                               String eventName, String studentName, String studentEmail) {
        this.registrationId = registrationId;
        this.formData = formData;
        this.status = status;
        this.submissionDate = submissionDate;
        this.eventName = eventName;
        this.studentName = studentName;
        this.studentEmail = studentEmail;
    }

    // --- Getters & Setters (Setters added for manual mapping in Controller) ---
    public Integer getRegistrationId() { return registrationId; }
    public void setRegistrationId(Integer registrationId) { this.registrationId = registrationId; }

    public String getFormData() { return formData; }
    public void setFormData(String formData) { this.formData = formData; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getSubmissionDate() { return submissionDate; }
    public void setSubmissionDate(LocalDateTime submissionDate) { this.submissionDate = submissionDate; }

    public String getEventName() { return eventName; }
    public void setEventName(String eventName) { this.eventName = eventName; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getStudentEmail() { return studentEmail; }
    public void setStudentEmail(String studentEmail) { this.studentEmail = studentEmail; }
}