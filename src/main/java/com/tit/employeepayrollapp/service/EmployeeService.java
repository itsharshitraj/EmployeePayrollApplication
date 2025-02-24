package com.tit.employeepayrollapp.service;

import com.tit.employeepayrollapp.dto.EmployeeDTO;
import com.tit.employeepayrollapp.model.Employee;
import com.tit.employeepayrollapp.repository.EmployeeRepository;
import com.tit.employeepayrollapp.exception.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    private final List<Employee> inMemoryEmployees = new CopyOnWriteArrayList<>();

    // ✅ Save Employee (Applies @Valid for validation)
    public Employee saveEmployee(@Valid EmployeeDTO employeeDTO) {
        log.info("Creating Employee: Name = {}, Salary = {}", employeeDTO.getName(), employeeDTO.getSalary());

        Employee employee = new Employee();
        employee.setName(employeeDTO.getName());
        employee.setSalary(employeeDTO.getSalary());

        inMemoryEmployees.add(employee);

        return employeeRepository.save(employee);
    }

    // ✅ Persist in-memory employees (Added better handling)
    public void persistEmployees() {
        List<Employee> validEmployees = inMemoryEmployees.stream()
                .filter(emp -> emp.getSalary() >= 1000)
                .collect(Collectors.toList());

        if (!validEmployees.isEmpty()) {
            employeeRepository.saveAll(validEmployees);
            inMemoryEmployees.removeAll(validEmployees);
            log.info("Successfully persisted {} employees", validEmployees.size());
        } else {
            log.warn("No valid employees found for persistence.");
        }
    }

    // ✅ Get All Employees
    public List<EmployeeDTO> getAllEmployees() {
        List<EmployeeDTO> employees = new ArrayList<>();
        inMemoryEmployees.forEach(emp -> employees.add(new EmployeeDTO(emp.getName(), emp.getSalary())));
        employees.addAll(employeeRepository.findAll().stream()
                .map(emp -> new EmployeeDTO(emp.getName(), emp.getSalary()))
                .collect(Collectors.toList()));
        return employees;
    }

    // ✅ Get Employee By ID (Throws `ResourceNotFoundException` if not found)
    public EmployeeDTO getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + id));
        return new EmployeeDTO(employee.getName(), employee.getSalary());
    }

    // ✅ Update Employee (Applies @Valid for validation)
    public Employee updateEmployee(Long id, @Valid EmployeeDTO updatedEmployeeDTO) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + id));

        employee.setName(updatedEmployeeDTO.getName());
        employee.setSalary(updatedEmployeeDTO.getSalary());

        return employeeRepository.save(employee);
    }

    // ✅ Delete Employee (Throws `ResourceNotFoundException` if ID does not exist)
    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + id));
        employeeRepository.delete(employee);
    }
}
