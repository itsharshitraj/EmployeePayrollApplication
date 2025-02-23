package com.tit.employeepayrollapp.service;

import com.tit.employeepayrollapp.dto.EmployeeDTO;
import com.tit.employeepayrollapp.model.Employee;
import com.tit.employeepayrollapp.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.tit.employeepayrollapp.exception.ResourceNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private  EmployeeRepository employeeRepository;

    // Save Employee (Called by POST /employeeservice/create)
    public Employee saveEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setName(employeeDTO.getName());
        employee.setSalary(employeeDTO.getSalary());
        return employeeRepository.save(employee);
    }

    // Get All Employees (Called by GET /employeeservice/)
    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(emp -> new EmployeeDTO(emp.getName(), emp.getSalary()))
                .collect(Collectors.toList());
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
