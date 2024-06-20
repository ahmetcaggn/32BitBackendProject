package com.toyota.UserManagement.service;

import com.toyota.UserManagement.dto.CreateUserRequest;
import com.toyota.UserManagement.dto.EmployeeDto;
import com.toyota.UserManagement.repository.EmployeeRepository;
import com.toyota.UserManagement.dto.EmployeeRequest;
import com.toyota.entity.Employee;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
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
        return employeeDtoList;
    }

    public EmployeeDto getEmployeeByUsername(String username) throws Exception {
        Employee employee = employeeRepository.findByUsername(username).orElseThrow(Exception::new);
        return new EmployeeDto(employee);
    }

    public EmployeeDto updateEmployeeById(Long id, EmployeeRequest er) {
        Employee employee = employeeRepository.findByIsDeletedFalseAndId(id);
        employee.setName(er.getName());
        employee.setSurname(er.getSurname());
        employee.setAddress(er.getAddress());
        employee.setPhoneNo(er.getPhoneNo());

        Employee savedEmployee = employeeRepository.save(employee);
        return new EmployeeDto(savedEmployee);

    }

    public EmployeeDto saveEmployee(CreateUserRequest employeeRequest) {
        Employee employee = Employee.builder()
                .name(employeeRequest.getName())
                .surname(employeeRequest.getSurname())
                .address(employeeRequest.getAddress())
                .phoneNo(employeeRequest.getPhoneNo())
                .username(employeeRequest.getUsername())
                .isDeleted(false)
                .password(passwordEncoder.encode(employeeRequest.getPassword()))
                .roles(employeeRequest.getRoles())
                .build();

        Employee savedEmployee = employeeRepository.save(employee);
        return new EmployeeDto(savedEmployee);
    }

    public Boolean deleteEmployeeById(Long id) {
        Employee employee = employeeRepository.findByIsDeletedFalseAndId(id);
        employee.setIsDeleted(true);
        employeeRepository.save(employee);
        return true;
    }
}
