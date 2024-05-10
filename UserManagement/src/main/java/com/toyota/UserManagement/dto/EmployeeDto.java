package com.toyota.UserManagement.dto;

import com.toyota.UserManagement.entity.Employee;
import com.toyota.UserManagement.entity.Role;
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
    private Long phoneNo;
    private Set<Role> roleList;

    public EmployeeDto(Employee employee){
        this.id = employee.getId();
        this.name = employee.getName();
        this.surname = employee.getSurname();
        this.address = employee.getAddress();
        this.phoneNo = employee.getPhoneNo();
        this.roleList = employee.getRoles();
    }

}
