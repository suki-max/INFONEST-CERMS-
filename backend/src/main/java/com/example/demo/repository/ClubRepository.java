package com.example.demo.repository;

import com.example.demo.entity.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// @Repository batata hai ki yeh database se baat karega
@Repository
// Yeh ek interface hai, class nahi
// JpaRepository humein CRUD operations (Create, Read, Update, Delete)
// ke saare functions (jaise findAll(), findById(), save()) free mein deta hai.
public interface ClubRepository extends JpaRepository<Club, String> {
    // Abhi ke liye yeh khaali rahega. JpaRepository sab sambhaal lega!
}