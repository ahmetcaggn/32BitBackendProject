package com.example.UserManagement.dto;

import com.example.UserManagement.entity.Role;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoleDto {
    private Long id;
    private String name;

    public RoleDto(Role role){
        this.id = role.getId();
        this.name = role.getName();
    }
}
