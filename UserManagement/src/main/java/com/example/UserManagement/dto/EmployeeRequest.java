package com.example.UserManagement.dto;

import com.example.UserManagement.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EmployeeRequest {
    private String name;
    private String surname;
    private String address;
    private Long phoneNo;
}
