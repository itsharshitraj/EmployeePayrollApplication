package com.tit.employeepayrollapp.service;

import com.tit.employeepayrollapp.dto.EmployeeDTO;
import com.tit.employeepayrollapp.model.Employee;
import com.tit.employeepayrollapp.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.tit.employeepayrollapp.exception.ResourceNotFoundException;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private  EmployeeRepository employeeRepository;

    private final List<Employee> inMemoryEmployees = new CopyOnWriteArrayList<>();
    // Save Employee (Called by POST /employeeservice/create)
    public Employee saveEmployee(EmployeeDTO employeeDTO) {
        System.out.println("Creating Employee: Name = " + employeeDTO.getName() + ", Salary = " + employeeDTO.getSalary()); // Debug Log

        Employee employee = new Employee();
        employee.setName(employeeDTO.getName());
        employee.setSalary(employeeDTO.getSalary()); // Ensure Salary is set

        System.out.println("Stored Employee: " + employee); // Debug Log
       // Add to in-memory list before persisting
        inMemoryEmployees.add(employee);

        return employeeRepository.save(employee);
    }


    // Persist in-memory employees to the database
    public void persistEmployees() {
        List<Employee> validEmployees = inMemoryEmployees.stream()
                .filter(emp -> emp.getSalary() >= 1000) // Ensure only valid salaries
                .collect(Collectors.toList());

        if (!validEmployees.isEmpty()) {
            employeeRepository.saveAll(validEmployees); // Save valid employees
            inMemoryEmployees.removeAll(validEmployees); // Remove only persisted employees
        } else {
            throw new RuntimeException("No valid employees to persist. Ensure all employees have a salary of at least 1000.");
        }
    }


    // Get All Employees (Called by GET /employeeservice/)
    public List<EmployeeDTO> getAllEmployees() {
        List<EmployeeDTO> employees = new ArrayList<>();
        inMemoryEmployees.forEach(emp -> employees.add(new EmployeeDTO(emp.getName(), emp.getSalary())));
        employees.addAll(employeeRepository.findAll().stream()
                .map(emp -> new EmployeeDTO(emp.getName(), emp.getSalary()))
                .collect(Collectors.toList()));
        return employees;
    }


    // Get Employee By ID (Called by GET /employeeservice/get/{id})
    public EmployeeDTO getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + id));
        return new EmployeeDTO(employee.getName(), employee.getSalary());
    }

// Update Employee (Called by PUT /employeeservice/update/{id})
public Employee updateEmployee(Long id, EmployeeDTO updatedEmployeeDTO) {
    Employee employee = employeeRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + id));
    employee.setName(updatedEmployeeDTO.getName());
    employee.setSalary(updatedEmployeeDTO.getSalary());
    return employeeRepository.save(employee);
}

    // Delete Employee (Called by DELETE /employeeservice/delete/{id})
    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + id));
        employeeRepository.delete(employee);
    }

}
