package com.toyota.security.interfaces;

import com.toyota.security.dto.EmployeeDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


@FeignClient(value = "USERMANAGEMENT")
public interface UserManagementInterface {
    @GetMapping("/employee/{username}")
    public EmployeeDto fetchEmployeeByUsername(@PathVariable String username) throws Exception;
}
