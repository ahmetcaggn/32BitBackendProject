package com.toyota.UserManagement.service;

import com.toyota.UserManagement.dto.*;
import com.toyota.UserManagement.dto.EmployeeDto;
import com.toyota.UserManagement.entity.Employee;
import com.toyota.UserManagement.entity.Role;
import com.toyota.UserManagement.repository.EmployeeRepository;
import com.toyota.UserManagement.repository.RoleRepository;
import com.toyota.UserManagement.dto.EmployeeRequest;
import com.toyota.UserManagement.dto.RoleDto;
import com.toyota.UserManagement.dto.RoleRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserManagementService {
    public RoleRepository roleRepository;
    public EmployeeRepository employeeRepository;

    @Autowired
    public UserManagementService(RoleRepository roleRepository, EmployeeRepository employeeRepository) {
        this.roleRepository = roleRepository;
        this.employeeRepository = employeeRepository;
    }

    public RoleDto createRole(RoleRequest roleRequest){

        Role role = new Role();
        role.setName(roleRequest.getName());
        Role createdRole = roleRepository.save(role);
        return new RoleDto(createdRole);
    }
    public List<RoleDto> getAllRoles(){
        List<Role> fetchedRoles = roleRepository.findAll();
        List<RoleDto> roleDtoList = new ArrayList<>();
        for (Role role : fetchedRoles){
            roleDtoList.add(new RoleDto(role));
        }
        return roleDtoList;
    }

    public List<EmployeeDto> getAllEmployees(){
        List<Employee> fetchedEmployeeRole = employeeRepository.findAllByIsDeletedFalse();
        List<EmployeeDto> employeeDtoList = new ArrayList<>();
        for (Employee employee : fetchedEmployeeRole){
            EmployeeDto employeeDto = new EmployeeDto(employee);
            employeeDtoList.add(employeeDto);
        }
        return employeeDtoList;
    }

    public EmployeeDto updateEmployeeById(Long id, EmployeeRequest er){
        Employee employee = employeeRepository.findByIsDeletedFalseAndId(id);
        employee.setName(er.getName());
        employee.setSurname(er.getSurname());
        employee.setAddress(er.getAddress());
        employee.setPhoneNo(er.getPhoneNo());

        Employee savedEmployee = employeeRepository.save(employee);
        return new EmployeeDto(savedEmployee);

    }

    public EmployeeDto saveEmployee(EmployeeRequest employeeRequest){
        Employee employee = new Employee();
        employee.setName(employeeRequest.getName());
        employee.setSurname(employeeRequest.getSurname());
        employee.setAddress(employeeRequest.getAddress());
        employee.setPhoneNo(employeeRequest.getPhoneNo());

        Employee savedEmployee = employeeRepository.save(employee);
        return new EmployeeDto(savedEmployee);
    }

    public Boolean deleteEmployeeById(Long id){
        Employee employee = employeeRepository.findByIsDeletedFalseAndId(id);
        employee.setIsDeleted(true);
        employeeRepository.save(employee);
        return true;
    }

    public EmployeeDto attachRole(Long employeeId, Long roleId){

        Employee employee = employeeRepository.findByIsDeletedFalseAndId(employeeId);
        Role role = roleRepository.findById(roleId).get();

        employee.getRoles().add(role);
        role.getEmployees().add(employee);

        roleRepository.save(role);
        employeeRepository.save(employee);
        return new EmployeeDto(employee);
    }
}
