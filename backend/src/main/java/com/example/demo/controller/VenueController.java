package com.example.demo.controller;

import com.example.demo.entity.Venue;
import com.example.demo.repository.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // CORS Error ko rokne ke liye
public class VenueController {

    @Autowired
    private VenueRepository venueRepository;

    // Saare venues laane ke liye
    // URL: http://localhost:8081/api/venues
    @GetMapping("/api/venues")
    public List<Venue> getAllVenues() {
        return venueRepository.findAll();
    }
}