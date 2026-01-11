package com.example.demo.repository;

import com.example.demo.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
    
    // Abhi ke liye khaali, JpaRepository sab sambhaal lega.
    // @OneToOne relationship ki vajah se humein custom query ki zaroorat nahi hai.
}