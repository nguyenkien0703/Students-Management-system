package com.example.studentmanagementsystem.service;

import com.example.studentmanagementsystem.entity.Student;
import com.example.studentmanagementsystem.entity.User;

import java.util.List;

public interface StudentService {
    List<Student> getAllStudents();
    List<Student> getAllStudentsByUser(User user);
    Student saveStudent(Student student);
    Student getStudentById(Long id);
    Student updateStudent(Student student);
    void deleteStudentById(Long id);
}
