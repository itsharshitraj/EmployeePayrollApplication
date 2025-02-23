package com.tit.employeepayrollapp.controller;

import com.tit.employeepayrollapp.model.Employee;
import com.tit.employeepayrollapp.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employeeservice")
public class EmployeePayrollController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/create") /* Create Employee
      URL: http://localhost:8080/employeeservice/create */
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        Employee savedEmployee = employeeService.saveEmployee(employee);
        return ResponseEntity.ok(savedEmployee);
    }

    @GetMapping("/") /* Get All Employees
     * URL: http://localhost:8080/employeeservice/ */
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/get/{id}")/* Get Employee By ID
      URL: http://localhost:8080/employeeservice/get/{id} */
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employee);
    }

    @PutMapping("/update/{id}")/* Update Employee
     * URL: http://localhost:8080/employeeservice/update/{id}*/
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee updatedEmployee) {
        Employee employee = employeeService.updateEmployee(id, updatedEmployee);
        return ResponseEntity.ok(employee);
    }

    @DeleteMapping("/delete/{id}")/* Delete Employee
     * URL: http://localhost:8080/employeeservice/delete/{id}*/
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/test")
    public String testEndpoint() {
        return "Employee Payroll Service is running!";
    }

}

