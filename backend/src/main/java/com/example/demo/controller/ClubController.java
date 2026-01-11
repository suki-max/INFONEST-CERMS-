package com.example.demo.controller;

import com.example.demo.entity.*;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.lang.NonNull; // Yeh zaroori hai
import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ClubController {

    @Autowired private ClubRepository clubRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private EventRepository eventRepository;

    @GetMapping("/clubs")
    public List<Club> getAllClubs() {
        return clubRepository.findAll();
    }

    @GetMapping("/clubs/{id}")
    public ResponseEntity<Map<String, Object>> getClubDetails(@PathVariable @NonNull String id) {
        // Warning hatane ke liye explicit check
        if (id == null || id.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        // findById(id) ko safe banane ke liye Optional handling
        Club club = clubRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Club not found"));

        // PostgreSQL Native Query use karke officials nikalna
        List<User> officials = userRepository.findByClubId(id);

        // Club ID ke basis par events nikalna
        List<Event> events = eventRepository.findByClubId(id);

        Map<String, Object> response = new HashMap<>();
        response.put("club", club);
        response.put("officials", officials);
        response.put("events", events);

        return ResponseEntity.ok(response);
    }
}