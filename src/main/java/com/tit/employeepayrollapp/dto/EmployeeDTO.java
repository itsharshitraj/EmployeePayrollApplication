package com.tit.employeepayrollapp.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmployeeDTO {

    @NotBlank(message = "Name is required")
    private String name;

    @Min(value = 1000, message = "Salary must be at least 1000")
    private double salary;
}