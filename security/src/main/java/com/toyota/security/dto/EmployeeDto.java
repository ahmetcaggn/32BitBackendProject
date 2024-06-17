package com.toyota.security.dto;

import com.toyota.entity.Employee;
import com.toyota.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EmployeeDto {
    private Long id;
    private String name;
    private String surname;
    private String address;
    private String phoneNo;
    private String username;
    private String password;
    private Set<Role> roles;

    public EmployeeDto(Employee employee){
        this.id = employee.getId();
        this.name = employee.getName();
        this.surname = employee.getSurname();
        this.address = employee.getAddress();
        this.phoneNo = employee.getPhoneNo();
        this.username = employee.getUsername();
        this.password = employee.getPassword();
        this.roles = employee.getRoles();
    }

}
