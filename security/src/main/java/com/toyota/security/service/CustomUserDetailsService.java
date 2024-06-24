package com.toyota.security.service;

import com.toyota.entity.Employee;
import com.toyota.security.dto.EmployeeDto;
import com.toyota.security.interfaces.UserManagementInterface;
import com.toyota.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserManagementInterface userManagementInterface;
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return userRepository.findByUsername(username).orElseThrow(
//                () -> new UsernameNotFoundException("User not found with username: " + username)
//        );
        try {
            EmployeeDto employeeDto = userManagementInterface.fetchEmployeeByUsername(username);
            return Employee.builder()
                    .id(employeeDto.getId())
                    .name(employeeDto.getName())
                    .surname(employeeDto.getSurname())
                    .address(employeeDto.getAddress())
                    .phoneNo(employeeDto.getPhoneNo())
                    .username(employeeDto.getUsername())
                    .isDeleted(false)
                    .roles(employeeDto.getRoles())
                    .password(employeeDto.getPassword())
                    .build();
        } catch (Exception e) {
            throw new UsernameNotFoundException(String.format("User not found with username: %s",username));
        }

    }
}
