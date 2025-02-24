package com.tit.employeepayrollapp.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmployeeDTO {

    @NotBlank(message = "Name is required")
    @JsonProperty("name")
    private String name;

    @Min(value = 1000, message = "Salary must be at least 1000")
    @JsonProperty("salary")
    private double salary;

    public double getSalary() {
        System.out.println("Getting salary: " + salary);
        return salary;
    }

    public void setSalary(double salary) {
        System.out.println("Setting salary: " + salary); // Debug Log
        this.salary = salary;
    }


}