package com.mini_project.employeemanagementsystem.service;

import com.mini_project.employeemanagementsystem.dto.CreateEmployeeDTO;
import com.mini_project.employeemanagementsystem.dto.EmployeeDTO;

import java.util.List;

public interface EmployeeService {
    List<EmployeeDTO> getAllEmployees();

    EmployeeDTO addEmployee(CreateEmployeeDTO employeeDTO);

    EmployeeDTO getEmployeeById(Long id);

    EmployeeDTO updateEmployee(EmployeeDTO employeeDTO);

    String deleteEmployee(Long id);
}
