package com.toyota.UserManagement.dto;

import com.toyota.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {
    private String name;
    private String surname;
    private String address;
    private String phoneNo;
    private String username;
    private String password;
    private Set<Role> roles;
}
