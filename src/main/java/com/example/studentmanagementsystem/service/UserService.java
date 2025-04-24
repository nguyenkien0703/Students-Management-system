package com.example.studentmanagementsystem.service;

import com.example.studentmanagementsystem.entity.User;

public interface UserService {
    User registerUser(User user);
    User findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
} 