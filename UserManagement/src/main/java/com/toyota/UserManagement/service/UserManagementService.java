package com.toyota.UserManagement.service;

import com.toyota.UserManagement.Exception.EmployeeNotFoundException;
import com.toyota.UserManagement.dto.EmployeeDto;
import com.toyota.UserManagement.dto.EmployeeDtoWithPwd;
import com.toyota.UserManagement.repository.EmployeeRepository;
import com.toyota.UserManagement.dto.EmployeeRequestDto;
import com.toyota.entity.Employee;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class UserManagementService {
    public final PasswordEncoder passwordEncoder;
    public final EmployeeRepository employeeRepository;

    public UserManagementService(PasswordEncoder passwordEncoder, EmployeeRepository employeeRepository) {
        this.passwordEncoder = passwordEncoder;
        this.employeeRepository = employeeRepository;
    }

    public List<EmployeeDto> getAllEmployees() {
        List<Employee> fetchedEmployeeRole = employeeRepository.findAllByIsDeletedFalse();
        List<EmployeeDto> employeeDtoList = new ArrayList<>();
        for (Employee employee : fetchedEmployeeRole) {
            EmployeeDto employeeDto = new EmployeeDto(employee);
            employeeDtoList.add(employeeDto);
        }
        log.info("{} employees fetched", employeeDtoList.size());
        return employeeDtoList;
    }

    public EmployeeDtoWithPwd getEmployeeByUsername(String username)  {
        Employee employee = employeeRepository.findByUsernameAndIsDeletedFalse(username).orElseThrow(
                ()-> new EmployeeNotFoundException("There is no employee with username: " + username)
        );
        log.info("Employee with username {} fetched", username);
        return new EmployeeDtoWithPwd(employee);
    }

    public EmployeeDto updateEmployeeById(Long id, EmployeeRequestDto er) {
        Employee employee = employeeRepository.findByIsDeletedFalseAndId(id).orElseThrow(
                ()-> new EmployeeNotFoundException("There is no employee with id: " + id)
        );
        employee.setName(er.getName());
        employee.setSurname(er.getSurname());
        employee.setUsername(er.getUsername());
        employee.setPassword(passwordEncoder.encode(er.getPassword()));
        employee.setAddress(er.getAddress());
        employee.setPhoneNo(er.getPhoneNo());
        employee.setRoles(er.getRoles());

        Employee savedEmployee = employeeRepository.save(employee);
        log.info("Employee with id {} updated", id);
        return new EmployeeDto(savedEmployee);
    }

    public EmployeeDto saveEmployee(EmployeeRequestDto employeeRequestDto) {
        Employee employee = Employee.builder()
                .name(employeeRequestDto.getName())
                .surname(employeeRequestDto.getSurname())
                .address(employeeRequestDto.getAddress())
                .phoneNo(employeeRequestDto.getPhoneNo())
                .username(employeeRequestDto.getUsername())
                .isDeleted(false)
                .password(passwordEncoder.encode(employeeRequestDto.getPassword()))
                .roles(employeeRequestDto.getRoles())
                .build();

        Employee savedEmployee = employeeRepository.save(employee);
        log.info("Employee has been created with id {}", savedEmployee.getId());
        return new EmployeeDto(savedEmployee);
    }

    public Boolean deleteEmployeeById(Long id) {
        Employee employee = employeeRepository.findByIsDeletedFalseAndId(id).orElseThrow(
                ()-> new EmployeeNotFoundException("There is no employee with id: " + id)
        );
        employee.setIsDeleted(true);
        employeeRepository.save(employee);
        log.info("Employee with id {} deleted", id);
        return true;
    }
}
