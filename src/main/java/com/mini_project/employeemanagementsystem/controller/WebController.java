package com.mini_project.employeemanagementsystem.controller;

import com.mini_project.employeemanagementsystem.common.exceptions.DataAdditionException;
import com.mini_project.employeemanagementsystem.common.exceptions.DataNotFoundException;
import com.mini_project.employeemanagementsystem.dto.CreateEmployeeDTO;
import com.mini_project.employeemanagementsystem.dto.EmployeeDTO;
import com.mini_project.employeemanagementsystem.mapper.EmployeeMapper;
import com.mini_project.employeemanagementsystem.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@Controller
@RequestMapping(value="/api/employees")
public class WebController {

    EmployeeService employeeService;

    // http://localhost:8081/api/employees/home
    /**
     * Renders the home page
     * @param model
     * @return home page
     */
    @GetMapping(value="/home")
    public String home(Model model){
        List<EmployeeDTO> employeeDTOList = employeeService.getAllEmployees();
        if(!employeeDTOList.isEmpty()){
            model.addAttribute("employeeList", employeeDTOList);
            model.addAttribute("isEmpty", false);
        }
        else{
            model.addAttribute("isEmpty", true);
        }
        return "index";
    }

    // http://localhost:8081/api/employees/view/{id}
    /**
     * On selection of a particular employee, shows that specific employee's details.
     * @param id
     * @param model
     * @return view-employee html page
     */
    @GetMapping(value = "/view/{id}")
    public String showEmployeeById(@PathVariable(value = "id") Long id, Model model){
        EmployeeDTO employeeDTO = employeeService.getEmployeeById(id);
        model.addAttribute("employee", employeeDTO);
        return "view-employee";
    }


    // http://localhost:8081/api/employees/add-employee
    /**
     * Renders the HTML form page for adding a new user
     * @param model
     * @return add-employee html page
     */
    @GetMapping(value="/add-employee")
    public String addEmployee(Model model){
        model.addAttribute("employee", new CreateEmployeeDTO());
        return "add-employee";
    }

    /**
     * Gets a new employee object and saves into the repository.
     * Loads the success or error page based on the outcome.
     * @param model
     * @param newEmployee
     * @return success or error page
     */
    @PostMapping(value = "/add")
    public String saveEmployee(Model model, @Valid @ModelAttribute CreateEmployeeDTO newEmployee){
        EmployeeDTO savedEmployee;
        try{
            savedEmployee = employeeService.addEmployee(newEmployee);
        }
        catch(DataAdditionException exception){
            model.addAttribute("message", "Error while adding new employee." );
            return "error";
        }
        model.addAttribute("message", "Employee added with employee ID: " + savedEmployee.getId() + "." );
        return "success";
    }

    // http://localhost:8081/api/employees/delete/{id}

    /**
     * Deletes the employee object upon clicking delete from the webpage
     * @param id
     * @param model
     * @return a success or an error page based on the outcome.
     */
    @GetMapping(value = "/delete/{id}")
    public String deleteEmployee(@PathVariable(value = "id") Long id, Model model){
        employeeService.deleteEmployee(id);
        try {
            employeeService.getEmployeeById(id);
        }
        catch(DataNotFoundException e){
            String message = "Successfully deleted employee from the database.";
            model.addAttribute("message", message);
            return "success";
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }
        model.addAttribute("message", "Error deleting the employee." );
        return "error";
    }

    // http://localhost:8081/api/employees/update/{id}

    /**
     * Updates the employee object upon clicking update from the webpage.
     * This method actually renders the webpage for the updation process.
     * @param id
     * @param model
     * @return a success or an error page based on the outcome.
     */
    @GetMapping(value = "/update-employee/{id}")
    public String updateEmployee(@PathVariable(value = "id") Long id, Model model){
        EmployeeDTO employee = employeeService.getEmployeeById(id);
        model.addAttribute("employee", employee);
        return "update-employee";
    }

    /**
     * Updates the selected employee object with the values filled from the webpage.
     * Loads the success or error page based on the updation process.
     * @param model
     * @param newEmployee
     * @return success or error page
     */
    @PostMapping(value = "/update/{id}")
    public String saveUpdatedEmployee(@PathVariable(value = "id") Long id, Model model, @Valid @ModelAttribute CreateEmployeeDTO newEmployee){
        EmployeeDTO updatedEmployee = EmployeeMapper.mapToEmployeeDTOFromCreateEmployeeDTO(newEmployee);
        updatedEmployee.setId(id);
        EmployeeDTO savedEmployee;
        try{
            savedEmployee = employeeService.updateEmployee(updatedEmployee);
        }
        catch(DataAdditionException dataAdditionException){
            model.addAttribute("message", "Error while updating the employee" );
            return "error";
        }
        model.addAttribute("message", "Employee updated with employee ID: " + id + "." );
        return "success";
    }
}
