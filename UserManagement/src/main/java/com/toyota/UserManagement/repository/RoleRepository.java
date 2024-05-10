package com.toyota.UserManagement.repository;

import com.toyota.UserManagement.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
}
