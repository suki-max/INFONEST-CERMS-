package com.example.demo.config;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PasswordMigrationRunner implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("--- CHECKING FOR UN-ENCRYPTED PASSWORDS ---");
        
        List<User> users = userRepository.findAll();
        int count = 0;

        for (User user : users) {
            String currentPassword = user.getPassword();
            
            // Check agar password pehle se BCrypt hash nahi hai (BCrypt usually $2a$ se start hota hai)
            if (currentPassword != null && !currentPassword.startsWith("$2a$")) {
                
                // Plain text ko encrypt karein
                String encodedPassword = passwordEncoder.encode(currentPassword);
                user.setPassword(encodedPassword);
                userRepository.save(user);
                
                count++;
                System.out.println("Migrated user: " + user.getEmail());

            }
        }

        if (count > 0) {
            System.out.println("--- SUCCESS: Encrypted " + count + " existing passwords. ---");
        } else {
            System.out.println("--- ALL CLEAR: All passwords are already encrypted. ---");
        }
    }
}
