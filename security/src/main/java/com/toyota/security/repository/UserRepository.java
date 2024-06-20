package com.toyota.security.repository;

import com.toyota.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Employee,Long> {
    Optional<Employee> findByUsername(String username);
}
