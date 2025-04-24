package com.example.studentmanagementsystem.controller;

import com.example.studentmanagementsystem.entity.Student;
import com.example.studentmanagementsystem.entity.User;
import com.example.studentmanagementsystem.service.StudentService;
import com.example.studentmanagementsystem.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class StudentController {
    private final StudentService studentService;
    private final UserService userService;

    public StudentController(StudentService studentService, UserService userService) {
        this.studentService = studentService;
        this.userService = userService;
    }

    @GetMapping("/students")
    public String listStudents(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findByUsername(auth.getName());
        model.addAttribute("students", studentService.getAllStudentsByUser(currentUser));
        return "students";
    }

    @GetMapping("/students/new")
    public String createStudentForm(Model model) {
        Student student = new Student();
        model.addAttribute("student", student);
        return "create_student";
    }

    @PostMapping("/students")
    public String saveStudent(@ModelAttribute("student") Student student) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findByUsername(auth.getName());
        student.setCreatedBy(currentUser);
        studentService.saveStudent(student);
        return "redirect:/students";
    }

    @GetMapping("/students/edit/{id}")
    public String editStudentForm(@PathVariable Long id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findByUsername(auth.getName());
        Student student = studentService.getStudentById(id);
        
        if (student.getCreatedBy().getId().equals(currentUser.getId())) {
            model.addAttribute("student", student);
            return "edit_student";
        }
        return "redirect:/students";
    }

    @PostMapping("/students/{id}")
    public String updateStudent(@PathVariable Long id, @ModelAttribute("student") Student student) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findByUsername(auth.getName());
        Student existingStudent = studentService.getStudentById(id);
        
        if (existingStudent.getCreatedBy().getId().equals(currentUser.getId())) {
            existingStudent.setFirstName(student.getFirstName());
            existingStudent.setLastName(student.getLastName());
            existingStudent.setEmail(student.getEmail());
            studentService.updateStudent(existingStudent);
        }
        return "redirect:/students";
    }

    @GetMapping("/students/{id}")
    public String deleteStudent(@PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findByUsername(auth.getName());
        Student student = studentService.getStudentById(id);
        
        if (student.getCreatedBy().getId().equals(currentUser.getId())) {
            studentService.deleteStudentById(id);
        }
        return "redirect:/students";
    }
}