package com.example.demo.controller;

import com.example.demo.dto.TeacherDTO;
import com.example.demo.entity.Teacher;
import com.example.demo.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // CORS Error ko rokne ke liye
public class TeacherController {

    @Autowired
    private TeacherRepository teacherRepository;

    // Saare teachers ki combined details laane ke liye
    // URL: http://localhost:8081/api/teachers
    @GetMapping("/teachers")
    public List<TeacherDTO> getAllTeachers() {
        
        // 1. Saare Teacher objects database se fetch karo
        List<Teacher> teachers = teacherRepository.findAll();

        // 2. Har Teacher object ko TeacherDTO mein badlo
        // Isse hum teacher.getUser() se linked user ki details nikaal sakte hain
        return teachers.stream().map(teacher -> new TeacherDTO(
            teacher.getUserId(),
            teacher.getUser().getFirstName(), // Linked User se data aa raha hai
            teacher.getUser().getLastName(),  // Linked User se data aa raha hai
            teacher.getUser().getEmail(),     // Linked User se data aa raha hai
            teacher.getSchedulePdfPath(),
            teacher.getSittingCabin()
        )).collect(Collectors.toList());
    }
}