package com.example.demo.repository;

import com.example.demo.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
    
    // PostgreSQL native query with explicit casting for safety
    @Query(value = "SELECT * FROM event WHERE CAST(club_id AS TEXT) = :clubId", nativeQuery = true)
    List<Event> findByClubId(@Param("clubId") String clubId);
}