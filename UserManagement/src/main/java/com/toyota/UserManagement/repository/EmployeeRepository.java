package com.toyota.UserManagement.repository;

import com.toyota.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    Optional<Employee> findByUsername(String username);
    Optional<Employee> findByIsDeletedFalseAndId(Long id);
    List<Employee> findAllByIsDeletedFalse();

}
