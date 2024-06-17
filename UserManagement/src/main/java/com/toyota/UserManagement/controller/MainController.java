package com.toyota.UserManagement.controller;

import com.toyota.UserManagement.dto.CreateUserRequest;
import com.toyota.UserManagement.dto.EmployeeDto;
import com.toyota.UserManagement.service.UserManagementService;
import com.toyota.UserManagement.dto.EmployeeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MainController {

    public final UserManagementService userManagementService;

    @GetMapping("/employee")
    public List<EmployeeDto> fetchAllEmployees() {
        return userManagementService.getAllEmployees();
    }

    @GetMapping("/employee/{username}")
    public EmployeeDto fetchEmployeeByUsername(@PathVariable String username) throws Exception {
        return userManagementService.getEmployeeByUsername(username);
    }

    @PostMapping("/employee")
    public EmployeeDto saveEmployee(@RequestBody CreateUserRequest request) {
        return userManagementService.saveEmployee(request);
    }


    @PutMapping("/employee/{id}")
    public EmployeeDto updateEmployee(@PathVariable("id") Long id, @RequestBody EmployeeRequest employeeRequest) {
        return userManagementService.updateEmployeeById(id, employeeRequest);
    }

    @DeleteMapping("/employee/{id}")
    public Boolean deleteEmployee(@PathVariable("id") Long id) {
        return userManagementService.deleteEmployeeById(id);
    }


//    @GetMapping("/employee/deneme")
//    public EmployeeDto asd(){
//        return userManagementService.getAllEmployees().get(0);
////        return "feign client is working";
//    }


//    @PutMapping("/employee/{employeeId}/role/{roleId}")
//    public EmployeeDto attachRoleToEmployeeById(@PathVariable("employeeId") Long employeeId, @PathVariable("roleId") Long roleId){
//        return userManagementService.attachRole(employeeId,roleId);
//    }
}
