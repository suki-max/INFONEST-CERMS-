package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.entity.Booking;
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
@CrossOrigin(origins = "*")
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

        // 1. COMMON DATA: Sabhi roles ke liye common information
        response.put("allSchedules", scheduleRepository.findAll());
        response.put("allClubs", clubRepository.findAll());

        // 2. ROLE BASED LOGIC 
        
        // --- STUDENT ---
        if (userRole.equals("student")) {
            // Student ne jin club recruitments me apply kiya hai
            response.put("myApplications", recruitmentRepository.findByUser_UserId(userId));
        } 
        
        // --- FACULTY ---
        else if (userRole.equals("faculty")) {
            // Faculty ki apni bookings DTO format mein (Address ke saath)
            List<BookingDTO> facultyBookings = bookingRepository.findByUserIdNative(userId).stream()
                    .map(this::convertToBookingDTO)
                    .collect(Collectors.toList());
            response.put("myBookings", facultyBookings);
            // Agar faculty ne kisi event me apply kiya ho
            response.put("myApplications", recruitmentRepository.findByUser_UserId(userId));
        } 
        
        // --- CLUB OFFICIAL ---
        else if (userRole.equals("club_official")) {
            User official = userRepository.findById(userId).orElse(null);
            if (official != null && official.getClubId() != null) {
                // Uske specific club ke events
                response.put("myClubEvents", eventRepository.findByClubId(official.getClubId()));
                
                // Uske club ke liye aayi hui Recruitment applications
                List<EventRecruitmentDTO> apps = recruitmentRepository.findAll().stream()
                    .filter(app -> app.getEvent() != null && app.getEvent().getClubId().equals(official.getClubId()))
                    .map(app -> new EventRecruitmentDTO(
                        app.getRegistrationId(), 
                        app.getFormData(), 
                        app.getStatus(), 
                        app.getSubmissionDate(),
                        (app.getEvent() != null ? app.getEvent().getName() : "N/A"),
                        (app.getUser() != null ? app.getUser().getFirstName() + " " + app.getUser().getLastName() : "Anonymous"),
                        (app.getUser() != null ? app.getUser().getEmail() : "N/A")
                    ))
                    .sorted(Comparator.comparing(EventRecruitmentDTO::getSubmissionDate, Comparator.reverseOrder()))
                    .collect(Collectors.toList());
                
                response.put("submittedApplications", apps);
            }
            
            // Official ki apni personal bookings
            List<BookingDTO> officialBookings = bookingRepository.findByUserIdNative(userId).stream()
                    .map(this::convertToBookingDTO)
                    .collect(Collectors.toList());
            response.put("myBookings", officialBookings);
        } 
        
        // --- ADMIN ---
        else if (userRole.equals("admin")) {
            // Admin ko poora system overview dikhega
            List<BookingDTO> allBookings = bookingRepository.findAll().stream()
                    .map(this::convertToBookingDTO)
                    .collect(Collectors.toList());
            
            response.put("totalBookings", allBookings);
            response.put("totalUsersCount", userRepository.count());
            response.put("allClubsCount", clubRepository.count());
            response.put("recentActivities", eventRepository.findAll()); 
            // Admin mode me dashboard par apni bookings bhi dikhani ho:
            response.put("myBookings", allBookings.stream()
                .filter(b -> b.getUserName().contains("Admin")) // Filter logic as per need
                .collect(Collectors.toList()));
        }

        // --- OFFICE USER / MONITORING ---
        else if (userRole.equals("office_user") || userRole.equals("office")) {
            response.put("viewOnlyMode", true);
            response.put("allEvents", eventRepository.findAll());
            List<BookingDTO> allBookings = bookingRepository.findAll().stream()
                    .map(this::convertToBookingDTO)
                    .collect(Collectors.toList());
            response.put("allBookings", allBookings);
        }

        return ResponseEntity.ok(response);
    }

    /**
     * Helper Method: Booking Entity ko DTO mein convert karta hai.
     * Isse Circular Reference error nahi aata aur Address fetch ho jata hai.
     */
    private BookingDTO convertToBookingDTO(Booking b) {
        return new BookingDTO(
            b.getBookingId(), 
            b.getPurpose(), 
            b.getBookingDate(),
            b.getStartTime(), 
            b.getEndTime(),
            (b.getVenue() != null ? b.getVenue().getAddress() : "Venue Removed"),
            (b.getUser() != null ? b.getUser().getFirstName() + " " + b.getUser().getLastName() : "Unknown User"),
            (b.getEvent() != null ? b.getEvent().getName() : "General Purpose")
        );
    }

    
}