package com.example.demo.repository;

import com.example.demo.entity.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VenueRepository extends JpaRepository<Venue, Integer> {
    
    // Abhi ke liye khaali, JpaRepository saare basic 
    // commands (findAll, findById, etc.) khud sambhaal lega.

}