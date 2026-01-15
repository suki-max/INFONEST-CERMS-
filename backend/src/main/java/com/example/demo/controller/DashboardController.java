package com.example.demo.controller;

import com.example.demo.entity.*;
import com.example.demo.dto.EventRecruitmentDTO;
import com.example.demo.dto.BookingDTO;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.lang.NonNull;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "http://localhost:3000") // Explicitly allowing your frontend
public class DashboardController {

    @Autowired private BookingRepository bookingRepository;
    @Autowired private EventRecruitmentRepository recruitmentRepository;
    @Autowired private EventRepository eventRepository;
    @Autowired private ClassScheduleRepository scheduleRepository;
    @Autowired private ClubRepository clubRepository;
    @Autowired private UserRepository userRepository;

    @GetMapping("/{userId}/{role}")
    public ResponseEntity<Map<String, Object>> getDashboardData(
            @PathVariable @NonNull Integer userId, 
            @PathVariable String role) {
        
        Map<String, Object> response = new HashMap<>();
        String userRole = (role != null) ? role.toLowerCase() : "student";

        try {
            // 1. COMMON DATA (Available for everyone)
            response.put("allSchedules", scheduleRepository.findAll());
            response.put("allClubs", clubRepository.findAll());

            // 2. ROLE BASED LOGIC 
            
            // --- STUDENT ---
            if (userRole.equals("student")) {
                response.put("myApplications", recruitmentRepository.findByUser_UserId(userId));
            } 
            
            // --- FACULTY ---
            else if (userRole.equals("faculty")) {
                List<BookingDTO> facultyBookings = bookingRepository.findByUserIdNative(userId).stream()
                        .map(this::convertToBookingDTO)
                        .collect(Collectors.toList());
                response.put("myBookings", facultyBookings);
                response.put("myApplications", recruitmentRepository.findByUser_UserId(userId));
            } 
            
            // --- CLUB OFFICIAL ---
            else if (userRole.equals("club_official")) {
                User official = userRepository.findById(userId).orElse(null);
                if (official != null && official.getClubId() != null) {
                    String clubId = official.getClubId();
                    
                    // Specific club ke events
                    response.put("myClubEvents", eventRepository.findByClubId(clubId));
                    
                    // Filtered applications for this specific club
                    List<EventRecruitmentDTO> apps = recruitmentRepository.findAll().stream()
                        .filter(app -> app.getEvent() != null && clubId.equals(app.getEvent().getClubId()))
                        .map(app -> new EventRecruitmentDTO(
                            app.getRegistrationId(), 
                            app.getFormData(), 
                            app.getStatus(), 
                            app.getSubmissionDate(),
                            app.getEvent().getName(),
                            (app.getUser() != null ? app.getUser().getFirstName() + " " + app.getUser().getLastName() : "Anonymous"),
                            (app.getUser() != null ? app.getUser().getEmail() : "N/A")
                        ))
                        .sorted(Comparator.comparing(EventRecruitmentDTO::getSubmissionDate).reversed())
                        .collect(Collectors.toList());
                    
                    response.put("submittedApplications", apps);
                }
                
                // Official's personal bookings
                List<BookingDTO> officialBookings = bookingRepository.findByUserIdNative(userId).stream()
                        .map(this::convertToBookingDTO)
                        .collect(Collectors.toList());
                response.put("myBookings", officialBookings);
            } 
            
            // --- ADMIN ---
            else if (userRole.equals("admin")) {
                response.put("totalUsersCount", userRepository.count());
                response.put("allClubsCount", clubRepository.count());
                response.put("allEvents", eventRepository.findAll());
                
                List<BookingDTO> allBookings = bookingRepository.findAll().stream()
                        .map(this::convertToBookingDTO)
                        .collect(Collectors.toList());
                response.put("totalBookings", allBookings);
            }

            // --- OFFICE USER ---
            else if (userRole.equals("office_user") || userRole.equals("office")) {
                response.put("viewOnlyMode", true);
                response.put("allEvents", eventRepository.findAll());
                response.put("allBookings", bookingRepository.findAll().stream()
                        .map(this::convertToBookingDTO)
                        .collect(Collectors.toList()));
            }

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            // Error logging for debugging
            System.err.println("Dashboard Fetch Error: " + e.getMessage());
            response.put("error", "Failed to load dashboard data");
            return ResponseEntity.internalServerError().body(response);
        }
    }

    private BookingDTO convertToBookingDTO(Booking b) {
        if (b == null) return null;
        return new BookingDTO(
            b.getBookingId(), 
            b.getPurpose(), 
            b.getBookingDate(),
            b.getStartTime(), 
            b.getEndTime(),
            (b.getVenue() != null ? b.getVenue().getAddress() : "N/A"),
            (b.getUser() != null ? b.getUser().getFirstName() + " " + b.getUser().getLastName() : "Unknown"),
            (b.getEvent() != null ? b.getEvent().getName() : "General")
        );
    }
}