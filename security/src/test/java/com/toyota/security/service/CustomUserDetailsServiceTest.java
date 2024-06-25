package com.toyota.security.service;

import com.toyota.entity.Employee;
import com.toyota.security.dto.EmployeeDto;
import com.toyota.security.interfaces.UserManagementInterface;
import com.toyota.security.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class CustomUserDetailsServiceTest {
    private CustomUserDetailsService customUserDetailsService;
    private UserManagementInterface userManagementInterface;
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userManagementInterface = Mockito.mock(UserManagementInterface.class);
        userRepository = Mockito.mock(UserRepository.class);
        customUserDetailsService = new CustomUserDetailsService(userManagementInterface, userRepository);

    }

    @DisplayName("should return user details when employee exist with given username")
    @Test
    void shouldReturnUserDetails_WhenEmployeeExistWithGivenUsername() throws Exception {
        // given
        String username = "username";
        Employee employee = new Employee(1L, "name", "surname", "address", "phoneNo", username, "password", false, null);
        // when
        Mockito.when(userManagementInterface.fetchEmployeeByUsername(username)).thenReturn(new EmployeeDto(employee));
        UserDetails result = customUserDetailsService.loadUserByUsername(username);
        // then
        assertEquals(employee, result);
        Mockito.verify(userManagementInterface).fetchEmployeeByUsername(username);
    }

    @DisplayName("should throw UsernameNotFoundException when employee does not exist with given username")
    @Test
    void shouldThrowUsernameNotFoundException_WhenEmployeeDoesNotExistWithGivenUsername() throws Exception {
        // given
        String username = "username";
        // when
        Mockito.when(userManagementInterface.fetchEmployeeByUsername(username)).thenReturn(null);
        // then
        assertThrows(UsernameNotFoundException.class, () -> customUserDetailsService.loadUserByUsername(username));
        Mockito.verify(userManagementInterface).fetchEmployeeByUsername(username);
    }

    @AfterEach
    void tearDown() {
    }
}