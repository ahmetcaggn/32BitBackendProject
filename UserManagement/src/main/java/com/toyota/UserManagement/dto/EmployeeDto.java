package com.toyota.UserManagement.dto;

import com.toyota.entity.Employee;
import com.toyota.entity.Role;
import lombok.*;

import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class EmployeeDto {
    private Long id;
    private String name;
    private String surname;
    private String address;
    private String phoneNo;
    private String username;
    private Set<Role> roles;

    public EmployeeDto(Employee employee){
        this.id = employee.getId();
        this.name = employee.getName();
        this.surname = employee.getSurname();
        this.address = employee.getAddress();
        this.phoneNo = employee.getPhoneNo();
        this.username = employee.getUsername();
        this.roles = employee.getRoles();
    }

}
