package com.example.demo.dto;

import java.time.LocalTime;

// Yeh DTO combined (joined) data ko frontend par bhejega
public class ClassScheduleDTO {

    private Integer scheduleId;
    private String subjectName;
    private String studentGroup;
    private String dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
    
    // Joined Data
    private Integer teacherUserId;
    private String teacherName;
    private String venueAddress;

    // Constructor
    public ClassScheduleDTO(Integer scheduleId, String subjectName, String studentGroup, 
                            String dayOfWeek, LocalTime startTime, LocalTime endTime,
                            Integer teacherUserId, String teacherName, String venueAddress) {
        this.scheduleId = scheduleId;
        this.subjectName = subjectName;
        this.studentGroup = studentGroup;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.teacherUserId = teacherUserId;
        this.teacherName = teacherName;
        this.venueAddress = venueAddress;
    }

    // --- Sirf Getters ---
    public Integer getScheduleId() { return scheduleId; }
    public String getSubjectName() { return subjectName; }
    public String getStudentGroup() { return studentGroup; }
    public String getDayOfWeek() { return dayOfWeek; }
    public LocalTime getStartTime() { return startTime; }
    public LocalTime getEndTime() { return endTime; }
    public Integer getTeacherUserId() { return teacherUserId; }
    public String getTeacherName() { return teacherName; }
    public String getVenueAddress() { return venueAddress; }
}