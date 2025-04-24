package com.example.studentmanagementsystem.service.impl;

import com.example.studentmanagementsystem.entity.Student;
import com.example.studentmanagementsystem.entity.User;
import com.example.studentmanagementsystem.repository.StudentRepository;
import com.example.studentmanagementsystem.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public List<Student> getAllStudentsByUser(User user) {
        return studentRepository.findAll().stream()
                .filter(student -> student.getCreatedBy().getId().equals(user.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
    }

    @Override
    public Student updateStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public void deleteStudentById(Long id) {
        studentRepository.deleteById(id);
    }
}
