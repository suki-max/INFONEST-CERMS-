package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "class_schedule")
public class ClassSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Integer scheduleId;

    @Column(name = "subject_name")
    private String subjectName;

    @Column(name = "student_group")
    private String studentGroup;

    @Column(name = "day_of_week")
    private String dayOfWeek;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    // --- Relationships (JOINs ke liye) ---

    // optional = true (default) batata hai ki user_id NULL ho sakti hai
    @ManyToOne(fetch = FetchType.EAGER) // Humne EAGER fetch add kiya hai
    @JoinColumn(name = "user_id")
    private User user;

    // optional = true (default) batata hai ki venue_id NULL ho sakti hai
    @ManyToOne(fetch = FetchType.EAGER) // Humne EAGER fetch add kiya hai
    @JoinColumn(name = "venue_id")
    private Venue venue;


    // --- Getters and Setters ---

    public Integer getScheduleId() { return scheduleId; }
    public void setScheduleId(Integer scheduleId) { this.scheduleId = scheduleId; }
    public String getSubjectName() { return subjectName; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }
    public String getStudentGroup() { return studentGroup; }
    public void setStudentGroup(String studentGroup) { this.studentGroup = studentGroup; }
    public String getDayOfWeek() { return dayOfWeek; }
    public void setDayOfWeek(String dayOfWeek) { this.dayOfWeek = dayOfWeek; }
    public LocalTime getStartTime() { return startTime; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }
    public LocalTime getEndTime() { return endTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public Venue getVenue() { return venue; }
    public void setVenue(Venue venue) { this.venue = venue; }
}