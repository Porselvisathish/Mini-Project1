package com.mini_project.employeemanagementsystem.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateEmployeeDTO {
    @NotBlank(message = "First Name cannot be blank")
    private String firstName;

    @NotBlank(message = "Last Name cannot be blank")
    private String lastName;

    @NotBlank(message = "Email cannot be blank")
    @Email
    private String email;
}
