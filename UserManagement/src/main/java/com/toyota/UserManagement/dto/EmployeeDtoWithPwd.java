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
public class EmployeeDtoWithPwd {
    private Long id;
    private String name;
    private String surname;
    private String address;
    private String phoneNo;
    private String username;
    private String password;
    private Set<Role> roles;

    public EmployeeDtoWithPwd(Employee employee){
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
