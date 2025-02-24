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
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    private final List<Employee> inMemoryEmployees = new CopyOnWriteArrayList<>();

    // Save Employee (Called by POST /employeeservice/create)
    public Employee saveEmployee(EmployeeDTO employeeDTO) {
        log.info("Creating Employee: Name = {}, Salary = {}", employeeDTO.getName(), employeeDTO.getSalary());

        Employee employee = new Employee();
        employee.setName(employeeDTO.getName());
        employee.setSalary(employeeDTO.getSalary());

        log.debug("Stored Employee in-memory: {}", employee);

        inMemoryEmployees.add(employee);
        Employee savedEmployee = employeeRepository.save(employee);
        log.info("Employee persisted to database: {}", savedEmployee);

        return savedEmployee;
    }

    // Persist in-memory employees to the database
    public void persistEmployees() {
        List<Employee> validEmployees = inMemoryEmployees.stream()
                .filter(emp -> emp.getSalary() >= 1000)
                .collect(Collectors.toList());

        if (!validEmployees.isEmpty()) {
            log.info("Persisting {} employees to the database.", validEmployees.size());
            employeeRepository.saveAll(validEmployees);
            inMemoryEmployees.removeAll(validEmployees);
            log.info("Successfully persisted employees.");
        } else {
            log.warn("No valid employees to persist. Ensure all employees have a salary of at least 1000.");
            throw new RuntimeException("No valid employees to persist.");
        }
    }

    // Get All Employees (Called by GET /employeeservice/)
    public List<EmployeeDTO> getAllEmployees() {
        log.info("Fetching all employees...");
        List<EmployeeDTO> employees = new ArrayList<>();
        inMemoryEmployees.forEach(emp -> employees.add(new EmployeeDTO(emp.getName(), emp.getSalary())));
        employees.addAll(employeeRepository.findAll().stream()
                .map(emp -> new EmployeeDTO(emp.getName(), emp.getSalary()))
                .collect(Collectors.toList()));
        log.info("Total employees fetched: {}", employees.size());
        return employees;
    }

    // Get Employee By ID (Called by GET /employeeservice/get/{id})
    public EmployeeDTO getEmployeeById(Long id) {
        log.info("Fetching employee with ID: {}", id);
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Employee not found with ID: {}", id);
                    return new ResourceNotFoundException("Employee not found with id " + id);
                });
        log.info("Employee found: {}", employee);
        return new EmployeeDTO(employee.getName(), employee.getSalary());
    }

    // Update Employee (Called by PUT /employeeservice/update/{id})
    public Employee updateEmployee(Long id, EmployeeDTO updatedEmployeeDTO) {
        log.info("Updating employee with ID: {}", id);
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Employee not found with ID: {}", id);
                    return new ResourceNotFoundException("Employee not found with id " + id);
                });

        employee.setName(updatedEmployeeDTO.getName());
        employee.setSalary(updatedEmployeeDTO.getSalary());
        Employee updatedEmployee = employeeRepository.save(employee);
        log.info("Employee updated successfully: {}", updatedEmployee);
        return updatedEmployee;
    }

    // Delete Employee (Called by DELETE /employeeservice/delete/{id})
    public void deleteEmployee(Long id) {
        log.info("Deleting employee with ID: {}", id);
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Employee not found with ID: {}", id);
                    return new ResourceNotFoundException("Employee not found with id " + id);
                });

        employeeRepository.delete(employee);
        log.info("Employee with ID {} deleted successfully.", id);
    }
}
