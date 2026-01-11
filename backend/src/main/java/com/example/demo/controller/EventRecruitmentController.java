package com.example.demo.controller;

import com.example.demo.dto.EventRecruitmentDTO;
import com.example.demo.entity.EventRecruitment;
import com.example.demo.repository.EventRecruitmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // CORS Error ko rokne ke liye
public class EventRecruitmentController {

    @Autowired
    private EventRecruitmentRepository eventRecruitmentRepository;

    // Saari applications (joined data ke saath) laane ke liye
    // URL: http://localhost:8081/api/applications
    @GetMapping("/applications")
    public List<EventRecruitmentDTO> getAllApplications() {
        return eventRecruitmentRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Ek event ki saari applications laane ke liye (Jaise, ACM ke saare)
    // URL: http://localhost:8081/api/applications/event/6
    @GetMapping("/applications/event/{eventId}")
    public List<EventRecruitmentDTO> getApplicationsByEvent(@PathVariable Integer eventId) {
        return eventRecruitmentRepository.findByEvent_EventId(eventId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Ek student ki saari applications laane ke liye
    // URL: http://localhost:8081/api/applications/user/16
    @GetMapping("/applications/user/{userId}")
    public List<EventRecruitmentDTO> getApplicationsByUser(@PathVariable Integer userId) {
        return eventRecruitmentRepository.findByUser_UserId(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    // --- Helper Method (NULL-Safe) ---
    // Yeh method Entity ko DTO mein badalta hai
    private EventRecruitmentDTO convertToDTO(EventRecruitment application) {
        
        // NULL values ko check karna
        String eventName = (application.getEvent() != null) ? application.getEvent().getName() : "Unknown Event";
        
        String studentName = (application.getUser() != null) ? 
                              application.getUser().getFirstName() + " " + application.getUser().getLastName() : 
                              "Unknown User";

        String studentEmail = (application.getUser() != null) ? application.getUser().getEmail() : "N/A";

        return new EventRecruitmentDTO(
            application.getRegistrationId(),
            application.getFormData(),
            application.getStatus(),
            application.getSubmissionDate(),
            eventName,
            studentName,
            studentEmail
        );
    }
}