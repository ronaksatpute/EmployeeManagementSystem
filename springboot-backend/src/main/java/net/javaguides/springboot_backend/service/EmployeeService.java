package net.javaguides.springboot_backend.service;

import java.util.List;
import net.javaguides.springboot_backend.model.Employee;

public interface EmployeeService {
    List<Employee> getAllEmployees();
    Employee saveEmployee(Employee employee);
    Employee getEmployeeById(Long id);
    Employee updateEmployee(Long id, Employee employeeDetails);
    void deleteEmployee(Long id);
}