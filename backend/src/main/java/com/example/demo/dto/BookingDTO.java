package com.example.demo.dto;

import java.time.LocalDate;
import java.time.LocalTime;

// Yeh DTO combined (joined) data ko frontend par bhejega
public class BookingDTO {

    private Integer bookingId;
    private String purpose;
    private LocalDate bookingDate;
    private LocalTime startTime;
    private LocalTime endTime;

    // Joined Data
    private String venueAddress;
    private String userName; // (jisne book kiya)
    private String eventName;

    // Constructor
    public BookingDTO(Integer bookingId, String purpose, LocalDate bookingDate, LocalTime startTime, LocalTime endTime,
                      String venueAddress, String userName, String eventName) {
        this.bookingId = bookingId;
        this.purpose = purpose;
        this.bookingDate = bookingDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.venueAddress = venueAddress;
        this.userName = userName;
        this.eventName = eventName;
    }

    // --- Sirf Getters ---
    public Integer getBookingId() { return bookingId; }
    public String getPurpose() { return purpose; }
    public LocalDate getBookingDate() { return bookingDate; }
    public LocalTime getStartTime() { return startTime; }
    public LocalTime getEndTime() { return endTime; }
    public String getVenueAddress() { return venueAddress; }
    public String getUserName() { return userName; }
    public String getEventName() { return eventName; }
}