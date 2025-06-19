package com.mini_project.employeemanagementsystem.service.Impl;

import com.mini_project.employeemanagementsystem.common.exceptions.DataAdditionException;
import com.mini_project.employeemanagementsystem.common.exceptions.DataNotFoundException;
import com.mini_project.employeemanagementsystem.dto.CreateEmployeeDTO;
import com.mini_project.employeemanagementsystem.dto.EmployeeDTO;
import com.mini_project.employeemanagementsystem.mapper.EmployeeMapper;
import com.mini_project.employeemanagementsystem.model.Employee;
import com.mini_project.employeemanagementsystem.repo.EmployeeRepository;
import com.mini_project.employeemanagementsystem.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    EmployeeRepository employeeRepository;

    /**
     * A service method to get all the employees from the database
     * @return a list of EmployeeDTO objects
     */
    @Override
    public List<EmployeeDTO> getAllEmployees() {
        List<EmployeeDTO> employeeDTOList = new ArrayList<>();
        List<Employee> employeeList = employeeRepository.findAll();
        employeeList.forEach(
                employee -> {
                    employeeDTOList.add(EmployeeMapper.mapToEmployeeDTO(employee));
                }
        );
        return employeeDTOList;
    }

    /**
     * A service method to persist an employee into the database
     * @return the employeeDTO if it is added successfully or throws DataAdditionException in case of an error
     */
    @Override
    public EmployeeDTO addEmployee(CreateEmployeeDTO employeeDTO) {
        Employee employee = employeeRepository.save(EmployeeMapper.mapToEmployeeFromCreateEmployeeDTO(employeeDTO));
        if(employee.getFirstName() == employeeDTO.getFirstName() &&
                employee.getLastName() == employeeDTO.getLastName() &&
                employee.getEmail() == employeeDTO.getEmail()
        ){
            return EmployeeMapper.mapToEmployeeDTO(employee);
        }
        String message = "Error while saving new Employee into the database";
        System.out.println(message);
        throw new DataAdditionException(message);
    }

    /**
     * A service method to get an employee with its id
     * @return the employeeDTO if it is added successfully or throws DataNotFoundException in case of an error
     */
    @Override
    public EmployeeDTO getEmployeeById(Long id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if(optionalEmployee.isEmpty()){
            String message = "Database does not have an employee with ID: " + id +  " to fetch.";
            System.out.println(message);
            throw new DataNotFoundException(message);
        }
        EmployeeDTO employeeDTO = EmployeeMapper.mapToEmployeeDTO(optionalEmployee.get());

        return employeeDTO;
    }

    /**
     * A service method to update an employee
     * @return the employeeDTO if it is added successfully or throws Exception in case of an error
     */
    @Override
    public EmployeeDTO updateEmployee(EmployeeDTO employeeDTO) {
        String message;
        getEmployeeById(employeeDTO.getId());
        Employee employee = employeeRepository.save(EmployeeMapper.mapToEmployee(employeeDTO));
        if(employee.getFirstName() == employeeDTO.getFirstName() &&
                employee.getLastName() == employeeDTO.getLastName() &&
                employee.getEmail() == employeeDTO.getEmail()
        ){
            return EmployeeMapper.mapToEmployeeDTO(employee);
        }
        message = "Error while updating the Employee into the database";
        System.out.println(message);
        throw new DataAdditionException(message);
    }

    /**
     * A service method to delete an employee with its id
     * @return a message if it is added successfully or throws DataNotFoundException in case of an error
     */
    @Override
    public String deleteEmployee(Long id) {
        if(employeeRepository.findById(id).isEmpty()){
            String message = "Database does not have an employee with ID: " + id +  " to delete.";
            System.out.println(message);
            throw new DataNotFoundException(message);
        }
        employeeRepository.deleteById(id);
        return "Deleted Employee with ID: " + id;
    }
}
