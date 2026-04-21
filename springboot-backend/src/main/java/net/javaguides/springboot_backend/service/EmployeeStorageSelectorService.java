package net.javaguides.springboot_backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import net.javaguides.springboot_backend.model.Employee;

@Service
public class EmployeeStorageSelectorService implements EmployeeService {

    private final EmployeeService jpaEmployeeService;
    private final EmployeeService csvEmployeeService;

    @Value("${employee.storage.type:mysql}")
    private String storageType;

    public EmployeeStorageSelectorService(
            @Qualifier("jpaEmployeeService") EmployeeService jpaEmployeeService,
            @Qualifier("csvEmployeeService") EmployeeService csvEmployeeService) {
        this.jpaEmployeeService = jpaEmployeeService;
        this.csvEmployeeService = csvEmployeeService;
    }

    private EmployeeService getActiveService() {
        if ("csv".equalsIgnoreCase(storageType)) {
            return csvEmployeeService;
        }
        return jpaEmployeeService;
    }

    @Override
    public List<Employee> getAllEmployees() {
        return getActiveService().getAllEmployees();
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        return getActiveService().saveEmployee(employee);
    }

    @Override
    public Employee getEmployeeById(Long id) {
        return getActiveService().getEmployeeById(id);
    }

    @Override
    public Employee updateEmployee(Long id, Employee employeeDetails) {
        return getActiveService().updateEmployee(id, employeeDetails);
    }

    @Override
    public void deleteEmployee(Long id) {
        getActiveService().deleteEmployee(id);
    }
}