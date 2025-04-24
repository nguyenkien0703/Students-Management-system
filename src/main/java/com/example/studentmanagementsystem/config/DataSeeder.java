package com.example.studentmanagementsystem.config;

import com.example.studentmanagementsystem.entity.Student;
import com.example.studentmanagementsystem.entity.User;
import com.example.studentmanagementsystem.repository.StudentRepository;
import com.example.studentmanagementsystem.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder; // Assuming Spring Security is used
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional; // Import Transactional

import java.util.Arrays;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder; // Inject PasswordEncoder

    public DataSeeder(UserRepository userRepository,
                      StudentRepository studentRepository,
                      PasswordEncoder passwordEncoder) { // Add PasswordEncoder to constructor
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional // Use Transactional to manage the session
    public void run(String... args) throws Exception {
        // Seed data only if the database is empty
        if (userRepository.count() == 0 && studentRepository.count() == 0) {
            seedData();
        }
    }

    private void seedData() {
        // --- Admin User 1 ---
        User admin1 = new User();
        admin1.setUsername("admin1");
        // Encode the password before saving
        admin1.setPassword(passwordEncoder.encode("password123"));
        admin1.setEmail("admin1@example.com");
        admin1.setRole("ADMIN"); // Role defaults to ADMIN, but explicitly setting is fine

        // --- Students for Admin 1 ---
        Student student1 = new Student("Alice", "Smith", "alice.smith@example.com");
        Student student2 = new Student("Bob", "Johnson", "bob.johnson@example.com");

        // Associate students with admin1
        admin1.addStudent(student1);
        admin1.addStudent(student2);

        // --- Admin User 2 ---
        User admin2 = new User();
        admin2.setUsername("admin2");
        admin2.setPassword(passwordEncoder.encode("password456"));
        admin2.setEmail("admin2@example.com");
        admin2.setRole("ADMIN");

        // --- Students for Admin 2 ---
        Student student3 = new Student("Charlie", "Brown", "charlie.brown@example.com");
        Student student4 = new Student("Diana", "Davis", "diana.davis@example.com");

        // Associate students with admin2
        admin2.addStudent(student3);
        admin2.addStudent(student4);


        // Save Admins (Students will be saved due to CascadeType.ALL)
        userRepository.saveAll(Arrays.asList(admin1, admin2));

        System.out.println("Seeded " + userRepository.count() + " admin users.");
        System.out.println("Seeded " + studentRepository.count() + " students.");
    }
} 