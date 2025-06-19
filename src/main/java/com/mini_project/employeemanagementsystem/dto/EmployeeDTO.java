package com.mini_project.employeemanagementsystem.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmployeeDTO{

    @Positive(message = "ID must be a positive number")
    @NotEmpty(message = "First Name cannot be empty")
    private Long id;

    @NotBlank(message = "First Name cannot be blank")
    private String firstName;

    @NotBlank(message = "Last Name cannot be blank")
    private String lastName;

    @NotBlank(message = "Email cannot be blank")
    @Email
    private String email;
}
