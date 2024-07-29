package com.toyota.UserManagement.controller;

import com.toyota.UserManagement.dto.EmployeeDto;
import com.toyota.UserManagement.dto.EmployeeDtoWithPwd;
import com.toyota.UserManagement.service.UserManagementService;
import com.toyota.UserManagement.dto.EmployeeRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employee")
public class MainController {

    public final UserManagementService userManagementService;

    @GetMapping("")
    public List<EmployeeDto> fetchAllEmployees() {
        return userManagementService.getAllEmployees();
    }

    @GetMapping("/{username}")
    public EmployeeDtoWithPwd fetchEmployeeByUsername(@PathVariable String username) {
        return userManagementService.getEmployeeByUsername(username);
    }

    @PostMapping("")
    public EmployeeDto saveEmployee(@RequestBody EmployeeRequestDto request) {
        return userManagementService.saveEmployee(request);
    }

    @PutMapping("/{id}")
    public EmployeeDto updateEmployee(@PathVariable("id") Long id, @RequestBody EmployeeRequestDto employeeRequestDto) {
        return userManagementService.updateEmployeeById(id, employeeRequestDto);
    }

    @DeleteMapping("/{id}")
    public Boolean deleteEmployee(@PathVariable("id") Long id) {
        return userManagementService.deleteEmployeeById(id);
    }


}
