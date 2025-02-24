package com.tit.employeepayrollapp.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.tit.employeepayrollapp.dto.EmployeeDTO;
import com.tit.employeepayrollapp.model.Employee;
import com.tit.employeepayrollapp.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(EmployeePayrollController.class)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Test
    public void testCreateEmployee() throws Exception {
        EmployeeDTO employeeDTO = new EmployeeDTO("Alice", 4000);
        Employee employee = new Employee(1L, "Alice", 4000);

        when(employeeService.saveEmployee(any(EmployeeDTO.class))).thenReturn(employee);

        mockMvc.perform(post("/employeeservice/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(employeeDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Alice"))
                .andExpect(jsonPath("$.salary").value(4000));
    }

    @Test
    public void testPersistEmployees() throws Exception {
        Mockito.doNothing().when(employeeService).persistEmployees();

        mockMvc.perform(post("/employeeservice/persist"))
                .andExpect(status().isOk())
                .andExpect(content().string("In-memory employees persisted to database"));
    }
}
