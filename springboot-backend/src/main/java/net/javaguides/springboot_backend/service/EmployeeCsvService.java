package net.javaguides.springboot_backend.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import net.javaguides.springboot_backend.exception.ResourceNotFoundException;
import net.javaguides.springboot_backend.model.Employee;

@Service("csvEmployeeService")
@ConditionalOnProperty(name = "employee.storage.type", havingValue = "csv")
public class EmployeeCsvService implements EmployeeService {

    @Value("${employee.csv.file:data/employees.csv}")
    private String csvFilePath;

    private Path path;

    @PostConstruct
    public void init() {
        try {
            path = Paths.get(csvFilePath);

            if (path.getParent() != null && Files.notExists(path.getParent())) {
                Files.createDirectories(path.getParent());
            }

            if (Files.notExists(path)) {
                Files.createFile(path);
                try (BufferedWriter writer = Files.newBufferedWriter(path)) {
                    writer.write("id,firstName,lastName,emailId");
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize CSV file", e);
        }
    }

    @Override
    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            boolean isHeader = true;

            while ((line = reader.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split(",", -1);
                if (parts.length >= 4) {
                    Employee employee = new Employee();
                    employee.setId(Long.parseLong(parts[0]));
                    employee.setFirstName(parts[1]);
                    employee.setLastName(parts[2]);
                    employee.setEmailId(parts[3]);
                    employees.add(employee);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read employees from CSV", e);
        }

        return employees;
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        List<Employee> employees = getAllEmployees();

        long nextId = employees.stream()
                .mapToLong(Employee::getId)
                .max()
                .orElse(0L) + 1;

        employee.setId(nextId);
        employees.add(employee);

        writeAllEmployees(employees);
        return employee;
    }

    @Override
    public Employee getEmployeeById(Long id) {
        return getAllEmployees().stream()
                .filter(employee -> employee.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id: " + id));
    }

    @Override
    public Employee updateEmployee(Long id, Employee employeeDetails) {
        List<Employee> employees = getAllEmployees();

        Employee employee = employees.stream()
                .filter(emp -> emp.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id: " + id));

        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setEmailId(employeeDetails.getEmailId());

        writeAllEmployees(employees);
        return employee;
    }

    @Override
    public void deleteEmployee(Long id) {
        List<Employee> employees = getAllEmployees();

        boolean removed = employees.removeIf(employee -> employee.getId() == id);

        if (!removed) {
            throw new ResourceNotFoundException("Employee not exist with id: " + id);
        }

        writeAllEmployees(employees);
    }

    private void writeAllEmployees(List<Employee> employees) {
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write("id,firstName,lastName,emailId");
            writer.newLine();

            for (Employee employee : employees) {
                writer.write(
                        employee.getId() + "," +
                        clean(employee.getFirstName()) + "," +
                        clean(employee.getLastName()) + "," +
                        clean(employee.getEmailId())
                );
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to write employees to CSV", e);
        }
    }

    private String clean(String value) {
        if (value == null) {
            return "";
        }
        return value.replace(",", " ");
    }
}