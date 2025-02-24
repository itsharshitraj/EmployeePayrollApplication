package com.tit.employeepayrollapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Min;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmployeeDTO {

    @NotBlank(message = "Name cannot be empty or null")
    @Pattern(regexp = "^[A-Z][a-zA-Z\\s]+$", message = "Name must start with a capital letter and contain only letters and spaces")
    @JsonProperty("name")
    private String name;

    @Min(value = 1000, message = "Salary must be at least 1000")
    @JsonProperty("salary")
    private double salary;
}
