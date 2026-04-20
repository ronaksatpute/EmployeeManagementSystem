package net.javaguides.springboot_backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;   // ✅ ADD THIS
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.javaguides.springboot_backend.model.Employee;
import net.javaguides.springboot_backend.repository.EmployeeRepository;

@CrossOrigin(origins = "http://localhost:4200")   // ✅ ADD THIS LINE
@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {
    
    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/employees")
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
}