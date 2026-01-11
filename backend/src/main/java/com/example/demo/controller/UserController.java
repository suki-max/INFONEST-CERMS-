package com.example.demo.controller;

import com.example.demo.dto.UserDTO;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") 
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    // --- 1. LOGIN ---
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        try {
            // Spring Security authentication process
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            
            // Database se user fetch karke convert to DTO
            User user = userRepository.findByEmail(loginRequest.getEmail());
            return ResponseEntity.ok(convertToDTO(user));

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error: Invalid email or password");
        }
    }

    // --- 2. REGISTER (Updated for Club ID and Selected Roles) ---
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {

        // Check karein ki email pehle se exists toh nahi karta
        if (userRepository.findByEmail(registerRequest.getEmail()) != null) {
            return ResponseEntity.status(400).body("Error: Email is already in use!");
        }

        // Auto-increment User ID using native order
        Optional<User> maxIdUser = userRepository.findTopByOrderByUserIdDesc();
        int newUserId = maxIdUser.map(user -> user.getUserId() + 1).orElse(1);

        User newUser = new User();
        newUser.setUserId(newUserId);
        newUser.setFirstName(registerRequest.getFirstName());
        newUser.setLastName(registerRequest.getLastName());
        newUser.setEmail(registerRequest.getEmail());
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        // Selected role ko format karke save karein
        String selectedRole = (registerRequest.getRole() != null) ? formatRole(registerRequest.getRole()) : "student";
        newUser.setRole(selectedRole);

        // AGAR user 'club_official' hai, toh hi Club ID save karo
        if (selectedRole.equals("club_official")) {
            newUser.setClubId(registerRequest.getClubId());
        } else {
            newUser.setClubId(null); // Baaki roles ke liye clubId blank rahegi
        }

        User savedUser = userRepository.save(newUser);
        return ResponseEntity.status(201).body(convertToDTO(savedUser));
    }

    // --- 3. GET ALL USERS ---
    @GetMapping("/users")
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // --- 4. UPDATE USER ---
    @PutMapping("/users/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable int userId, @RequestBody UserDTO userDetails) {
        User existingUser = userRepository.findById(userId).orElse(null);
        if (existingUser == null) return ResponseEntity.status(404).body("User not found");

        existingUser.setFirstName(userDetails.getFirstName());
        existingUser.setLastName(userDetails.getLastName());
        existingUser.setRole(formatRole(userDetails.getRole())); 
        existingUser.setClubId(userDetails.getClubId());
        
        User updatedUser = userRepository.save(existingUser);
        return ResponseEntity.ok(convertToDTO(updatedUser));
    }

    // --- 5. DELETE USER ---
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable int userId) {
        if (!userRepository.existsById(userId)) {
            return ResponseEntity.status(404).body("User not found");
        }
        userRepository.deleteById(userId);
        return ResponseEntity.ok("User deleted successfully");
    }

    // Helper method to create DTO
    private UserDTO convertToDTO(User user) {
        return new UserDTO(
            user.getUserId(), 
            user.getFirstName(), 
            user.getLastName(),
            user.getEmail(), 
            user.getRole(), 
            user.getClubId()
        );
    }

    // Logic to ensure role names match our system standards
    private String formatRole(String role) {
        String clean = role.trim().toLowerCase();
        if (clean.contains("admin")) return "admin";
        if (clean.contains("club")) return "club_official";
        if (clean.contains("office")) return "office_user";
        if (clean.contains("faculty")) return "faculty";
        return "student";
    }
}