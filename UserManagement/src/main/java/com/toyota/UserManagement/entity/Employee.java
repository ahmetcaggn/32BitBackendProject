package com.toyota.UserManagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.Set;

@Entity
@Getter
@Setter
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String surname;
    private String address;
    private Long phoneNo;
    @Column(nullable = false,insertable = false)
    @ColumnDefault("false")
    private Boolean isDeleted;

//    @OneToMany(fetch = FetchType.EAGER, targetEntity = EmployeeRole.class, mappedBy = "employee")
//    private Set<EmployeeRole> employeeRoles;

    @ManyToMany
    @JoinTable(name = "Employee_Role",
            joinColumns = @JoinColumn(name = "Employee_Id"),
            inverseJoinColumns = @JoinColumn(name = "Role_Id")
    )
    private Set<Role> roles;
}
