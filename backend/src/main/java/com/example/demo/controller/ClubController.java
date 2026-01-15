package com.example.demo.controller;

import com.example.demo.entity.*;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.lang.NonNull;
import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
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
        
        Optional<Club> clubOptional = clubRepository.findById(id);

        if (clubOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Club club = clubOptional.get();

        // Data fetching from repositories
        List<User> officials = userRepository.findByClubId(id);
        List<Event> events = eventRepository.findByClubId(id);

        Map<String, Object> response = new HashMap<>();
        response.put("club", club);
        response.put("officials", (officials != null) ? officials : new ArrayList<>());
        response.put("events", (events != null) ? events : new ArrayList<>());

        return ResponseEntity.ok(response);
    }
}