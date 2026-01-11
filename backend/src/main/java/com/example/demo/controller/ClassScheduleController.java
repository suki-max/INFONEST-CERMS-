package com.example.demo.controller;

import com.example.demo.dto.ClassScheduleDTO;
import com.example.demo.entity.ClassSchedule;
import com.example.demo.repository.ClassScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // CORS Error ko rokne ke liye
public class ClassScheduleController {

    @Autowired
    private ClassScheduleRepository classScheduleRepository;

    // Saare schedules (joined data ke saath) laane ke liye
    // URL: http://localhost:8081/api/schedule
    @GetMapping("/schedule")
    public List<ClassScheduleDTO> getAllSchedules() {
        List<ClassSchedule> schedules = classScheduleRepository.findAll();
        return schedules.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Ek teacher ka schedule dhoondhne ke liye
    // URL: http://localhost:8081/api/schedule/teacher/5
    @GetMapping("/schedule/teacher/{userId}")
    public List<ClassScheduleDTO> getScheduleByTeacher(@PathVariable Integer userId) {
        List<ClassSchedule> schedules = classScheduleRepository.findByUser_UserId(userId);
        return schedules.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Ek venue ka schedule dhoondhne ke liye
    // URL: http://localhost:8081/api/schedule/venue/1
    @GetMapping("/schedule/venue/{venueId}")
    public List<ClassScheduleDTO> getScheduleByVenue(@PathVariable Integer venueId) {
        List<ClassSchedule> schedules = classScheduleRepository.findByVenue_VenueId(venueId);
        return schedules.stream().map(this::convertToDTO).collect(Collectors.toList());
    }


    // --- Helper Method (UPDATED) ---
    // Yeh method ab NULL ko check karega
    private ClassScheduleDTO convertToDTO(ClassSchedule schedule) {
        
        // Agar user NULL hai, toh ID 0 aur naam "N/A" rakho
        Integer teacherId = (schedule.getUser() != null) ? schedule.getUser().getUserId() : 0;
        String teacherName = (schedule.getUser() != null) ? 
                              schedule.getUser().getFirstName() + " " + schedule.getUser().getLastName() : 
                              "N/A";
        
        // Agar venue NULL hai, toh address "N/A" rakho
        String venueAddress = (schedule.getVenue() != null) ? schedule.getVenue().getAddress() : "N/A";

        return new ClassScheduleDTO(
            schedule.getScheduleId(),
            schedule.getSubjectName(),
            schedule.getStudentGroup(),
            schedule.getDayOfWeek(),
            schedule.getStartTime(),
            schedule.getEndTime(),
            teacherId,
            teacherName,
            venueAddress
        );
    }
}