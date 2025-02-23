package com.tit.employeepayrollapp.service;

import com.tit.employeepayrollapp.model.Employee;
import com.tit.employeepayrollapp.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.tit.employeepayrollapp.exception.ResourceNotFoundException;
import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private  EmployeeRepository employeeRepository;

    // Save Employee (Called by POST /employeeservice/create)
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    // Get All Employees (Called by GET /employeeservice/)
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // Get Employee By ID (Called by GET /employeeservice/get/{id})
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + id));
    }

// Update Employee (Called by PUT /employeeservice/update/{id})
    public Employee updateEmployee(Long id, Employee updatedEmployee) {
        Employee employee = getEmployeeById(id);
        employee.setName(updatedEmployee.getName());
        employee.setSalary(updatedEmployee.getSalary());
        return employeeRepository.save(employee);
    }

    // Delete Employee (Called by DELETE /employeeservice/delete/{id})
    public void deleteEmployee(Long id) {
        Employee employee = getEmployeeById(id);
        employeeRepository.delete(employee);
    }

}
