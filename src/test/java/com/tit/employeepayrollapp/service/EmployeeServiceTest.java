package com.tit.employeepayrollapp.service;



import com.tit.employeepayrollapp.dto.EmployeeDTO;
import com.tit.employeepayrollapp.model.Employee;
import com.tit.employeepayrollapp.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @InjectMocks
    private EmployeeService employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Test
    public void testSaveEmployee() {
        EmployeeDTO employeeDTO = new EmployeeDTO("John Doe", 5000);
        Employee employee = new Employee(1L, "John Doe", 5000);

        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        Employee savedEmployee = employeeService.saveEmployee(employeeDTO);

        assertNotNull(savedEmployee);
        assertEquals("John Doe", savedEmployee.getName());
        assertEquals(5000, savedEmployee.getSalary(), 0);
    }

    @Test
    public void testPersistEmployees() {
        List<Employee> inMemoryEmployees = new ArrayList<>();
        inMemoryEmployees.add(new Employee(1L, "Alice", 1200));
        inMemoryEmployees.add(new Employee(2L, "Bob", 800)); // Invalid (less than 1000)

        when(employeeRepository.saveAll(anyList())).thenReturn(List.of(new Employee(1L, "Alice", 1200)));

        employeeService.persistEmployees();

        verify(employeeRepository, times(1)).saveAll(anyList());
    }
}
