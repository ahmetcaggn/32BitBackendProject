package com.example.UserManagement.repository;

import com.example.UserManagement.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    Employee findByIsDeletedFalseAndId(Long id);
    List<Employee> findAllByIsDeletedFalse();
}
