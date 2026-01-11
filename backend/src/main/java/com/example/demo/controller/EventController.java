package com.example.demo.controller;

import com.example.demo.entity.Event;
import com.example.demo.repository.EventRepository;
import com.example.demo.repository.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private VenueRepository venueRepository;

    // ===========================
    // GET ALL EVENTS
    // ===========================
    @GetMapping("/events")
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    // ===========================
    // GET EVENT BY ID + VENUE
    // ===========================
    @GetMapping("/events/{id}")
    public ResponseEntity<Map<String, Object>> getEventById(@PathVariable int id) {

        return eventRepository.findById(id)
                .map(event -> {

                    Map<String, Object> response = new HashMap<>();
                    response.put("eventId", event.getEventId());
                    response.put("name", event.getName());
                    response.put("description", event.getDescription());
                    response.put("date", event.getDate());
                    response.put("time", event.getTime());
                    response.put("deadline", event.getDeadline());
                    response.put("registrationFormLink", event.getRegistrationFormLink());
                    response.put("hidden", event.isHidden());

                    Integer venueId = event.getVenueId();
                    if (venueId != null) {
                        venueRepository.findById(venueId)
                                .ifPresent(v -> response.put("venueAddress", v.getAddress()));
                    } else {
                        response.put("venueAddress", "TBA");
                    }

                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ===========================
    // CREATE EVENT
    // ===========================
    @PostMapping("/events")
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
    return ResponseEntity.ok(eventRepository.save(event));
}



    // ===========================
    // TOGGLE VISIBILITY (FIXED)
    // ===========================
    @PutMapping("/events/{id}/toggle-visibility")
    public ResponseEntity<Event> toggleVisibility(
            @PathVariable int id,
            @RequestBody Map<String, Boolean> payload) {

        Boolean isHidden = payload.get("isHidden");
        if (isHidden == null) {
            return ResponseEntity.badRequest().build();
        }

        return eventRepository.findById(id)
                .map(event -> {
                    event.setHidden(isHidden);
                    Event updatedEvent = eventRepository.save(event);
                    return ResponseEntity.ok(updatedEvent);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // ===========================
    // DELETE EVENT
    // ===========================
    @DeleteMapping("/events/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable int id) {

        if (!eventRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        eventRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
