package com.example.demo.repository;

import com.example.demo.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

    // Native Query to fetch bookings by User ID
    @Query(value = "SELECT * FROM booking WHERE user_id = :userId", nativeQuery = true)
    List<Booking> findByUserIdNative(@Param("userId") Integer userId);

    // Native Query to fetch bookings by Venue ID
    @Query(value = "SELECT * FROM booking WHERE venue_id = :venueId", nativeQuery = true)
    List<Booking> findByVenueIdNative(@Param("venueId") Integer venueId);

    // Overlap Check Query with @Param annotations
    @Query("SELECT COUNT(b) FROM Booking b WHERE b.venue.venueId = :venueId " +
           "AND b.bookingDate = :date " +
           "AND (:startTime < b.endTime AND :endTime > b.startTime)")
    long countOverlappingBookings(
        @Param("venueId") Integer venueId, 
        @Param("date") LocalDate date, 
        @Param("startTime") LocalTime startTime, 
        @Param("endTime") LocalTime endTime
    );
}