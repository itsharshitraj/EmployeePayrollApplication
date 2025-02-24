package com.tit.employeepayrollapp.controller;

import com.tit.employeepayrollapp.dto.EmployeeDTO;
import com.tit.employeepayrollapp.model.Employee;
import com.tit.employeepayrollapp.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/employeeservice")
public class EmployeePayrollController {

    @Autowired
    private final EmployeeService employeeService;
    public EmployeePayrollController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    @GetMapping("/get/{id}")  // ✅ Ensure this annotation is correct
    public EmployeeDTO getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }
    @PostMapping("/create")
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
        Employee savedEmployee = employeeService.saveEmployee(employeeDTO);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @Valid @RequestBody EmployeeDTO employeeDTO) {
        Employee updatedEmployee = employeeService.updateEmployee(id, employeeDTO);
        return ResponseEntity.ok(updatedEmployee);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok("Employee deleted successfully!");
    }
}
