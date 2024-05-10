package com.toyota.UserManagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

//    @OneToMany(mappedBy = "roles")
//    private Set<EmployeeRole> employees;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private Set<Employee> employees;
}
