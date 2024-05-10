package com.toyota.UserManagement.controller;

import com.toyota.UserManagement.dto.*;
import com.toyota.UserManagement.dto.EmployeeDto;
import com.toyota.UserManagement.service.UserManagementService;
import com.toyota.UserManagement.dto.EmployeeRequest;
import com.toyota.UserManagement.dto.RoleDto;
import com.toyota.UserManagement.dto.RoleRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MainController {

    public UserManagementService userManagementService;

    @Autowired
    public MainController(UserManagementService userManagementService) {
        this.userManagementService = userManagementService;
    }


    @GetMapping("/")
    public String great(){
        return "Hello";
    }

    @GetMapping("/role")
    public List<RoleDto> fetchRoles(){
        return userManagementService.getAllRoles();
    }

    @PostMapping("/role")
    public RoleDto createRole(@RequestBody RoleRequest roleRequest){
        return userManagementService.createRole(roleRequest);
    }

    @GetMapping("/employee")
    public List<EmployeeDto> fetchEmloyees(){
        return userManagementService.getAllEmployees();
    }

    @PostMapping("/employee")
    public EmployeeDto saveEmployee(@RequestBody EmployeeRequest employeeRequest){
        return userManagementService.saveEmployee(employeeRequest);
    }

    @PutMapping("/employee/{id}")
    public EmployeeDto updateEmployee(@PathVariable("id") Long id, @RequestBody EmployeeRequest employeeRequest ){
        return userManagementService.updateEmployeeById(id,employeeRequest);
    }

    @DeleteMapping("/employee/{id}")
    public Boolean deleteEmployee(@PathVariable("id") Long id){
        return userManagementService.deleteEmployeeById(id);
    }


    @PutMapping("/employee/{employeeId}/role/{roleId}")
    public EmployeeDto attachRoleToEmployeeById(@PathVariable("employeeId") Long employeeId, @PathVariable("roleId") Long roleId){
        return userManagementService.attachRole(employeeId,roleId);
    }
}
