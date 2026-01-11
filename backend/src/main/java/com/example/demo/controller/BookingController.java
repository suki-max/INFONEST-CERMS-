package com.example.demo.controller;

import com.example.demo.dto.BookingDTO;
import com.example.demo.entity.Booking;
import com.example.demo.entity.User;
import com.example.demo.entity.Venue;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "*") 
public class BookingController {

    @Autowired private BookingRepository bookingRepository;
    @Autowired private VenueRepository venueRepository;
    @Autowired private UserRepository userRepository;

    @PostMapping("/check-availability")
    public ResponseEntity<Map<String, Object>> checkAvailability(@RequestBody Map<String, Object> payload) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Parsing String to correct Types to avoid casting errors
            Integer venueId = Integer.parseInt(payload.get("venueId").toString());
            LocalDate date = LocalDate.parse(payload.get("date").toString());
            LocalTime startTime = LocalTime.parse(payload.get("startTime").toString());
            LocalTime endTime = LocalTime.parse(payload.get("endTime").toString());

            long count = bookingRepository.countOverlappingBookings(venueId, date, startTime, endTime);
            
            response.put("available", count == 0);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("available", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createBooking(@RequestBody Map<String, Object> payload) {
        try {
            Integer venueId = Integer.parseInt(payload.get("venueId").toString());
            Integer userId = Integer.parseInt(payload.get("userId").toString());

            Venue venue = venueRepository.findById(venueId).orElse(null);
            User user = userRepository.findById(userId).orElse(null);
            
            if (venue == null || user == null) {
                return ResponseEntity.badRequest().body("User or Venue not found");
            }

            Booking booking = new Booking();
            booking.setVenue(venue);
            booking.setUser(user);
            booking.setBookingDate(LocalDate.parse(payload.get("bookingDate").toString()));
            booking.setStartTime(LocalTime.parse(payload.get("startTime").toString()));
            booking.setEndTime(LocalTime.parse(payload.get("endTime").toString()));
            booking.setPurpose(payload.get("purpose").toString());

            bookingRepository.save(booking);
            
            Map<String, String> successResponse = new HashMap<>();
            successResponse.put("status", "Success");
            return ResponseEntity.ok(successResponse);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public List<BookingDTO> getBookingsByUser(@PathVariable Integer userId) {
        return bookingRepository.findByUserIdNative(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private BookingDTO convertToDTO(Booking booking) {
        String venueAddress = (booking.getVenue() != null) ? booking.getVenue().getAddress() : "N/A";
        String userName = (booking.getUser() != null) ? 
                           booking.getUser().getFirstName() + " " + booking.getUser().getLastName() : "System";
        String eventName = (booking.getPurpose() != null) ? booking.getPurpose() : "General";

        return new BookingDTO(
            booking.getBookingId(),
            booking.getPurpose(),
            booking.getBookingDate(),
            booking.getStartTime(),
            booking.getEndTime(),
            venueAddress,
            userName,
            eventName
        );
    }
}