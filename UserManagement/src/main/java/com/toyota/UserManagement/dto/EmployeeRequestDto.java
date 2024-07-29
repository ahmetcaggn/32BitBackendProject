package com.toyota.UserManagement.dto;

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
public class EmployeeRequestDto {
    private String name;
    private String surname;
    private String username;
    private String password;
    private String address;
    private String phoneNo;
    private Set<Role> roles;
}
