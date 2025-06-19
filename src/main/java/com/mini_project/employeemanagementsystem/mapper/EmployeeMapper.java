package com.mini_project.employeemanagementsystem.mapper;

import com.mini_project.employeemanagementsystem.dto.CreateEmployeeDTO;
import com.mini_project.employeemanagementsystem.dto.EmployeeDTO;
import com.mini_project.employeemanagementsystem.model.Employee;

public class EmployeeMapper {
    public static Employee mapToEmployee(EmployeeDTO employeeDTO){
        return new Employee(employeeDTO.getId(),
                employeeDTO.getFirstName(),
                employeeDTO.getLastName(),
                employeeDTO.getEmail()
                );
    }
    public static EmployeeDTO mapToEmployeeDTO(Employee employee){
        return new EmployeeDTO(employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail()
        );
    }

    public static Employee mapToEmployeeFromCreateEmployeeDTO(CreateEmployeeDTO employeeDTO){
        return new Employee(null,
                employeeDTO.getFirstName(),
                employeeDTO.getLastName(),
                employeeDTO.getEmail()
        );
    }

    public static EmployeeDTO mapToEmployeeDTOFromCreateEmployeeDTO(CreateEmployeeDTO employeeDTO){
        return new EmployeeDTO(null,
                employeeDTO.getFirstName(),
                employeeDTO.getLastName(),
                employeeDTO.getEmail()
        );
    }
}
