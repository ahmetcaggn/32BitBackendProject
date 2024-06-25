package com.toyota.security.service;

import com.toyota.entity.Employee;
import com.toyota.entity.Role;
import com.toyota.security.dto.TokenValidateDto;
import com.toyota.security.util.JwtUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


class SecurityServiceTest {

    private SecurityService securityService;
    private JwtUtil jwtUtil;
    private CustomUserDetailsService userDetailsService;

    @BeforeEach
    void setUp() {
        jwtUtil = Mockito.mock(JwtUtil.class);
        userDetailsService = Mockito.mock(CustomUserDetailsService.class);
        securityService = new SecurityService(jwtUtil, userDetailsService);
    }

    @Test
    void validateToken() {
        // given
        String username = "username";
        Set<Role> roles = Set.of(Role.ADMIN, Role.CASHIER, Role.MANAGER);
        Employee employee = new Employee(1L, "name", "surname", "address", "phoneNo", username, "password", false, roles);
        String token = "token";
        // when
        Mockito.when(jwtUtil.extractUsername(token)).thenReturn(username);
        Mockito.when(userDetailsService.loadUserByUsername(username)).thenReturn(employee);
        Mockito.when(jwtUtil.validateToken(token, employee)).thenReturn(true);
        assertTrue(securityService.validateToken(new TokenValidateDto(token)));
        // then
        Mockito.verify(jwtUtil).extractUsername(token);
        Mockito.verify(userDetailsService).loadUserByUsername(username);
        Mockito.verify(jwtUtil).validateToken(token, employee);
    }

    @Test
    void generateToken() {
        // given
        String username = "username";
        Set<Role> roles = Set.of(Role.ADMIN, Role.CASHIER, Role.MANAGER);
        Employee employee = new Employee(1L, "name", "surname", "address", "phoneNo", username, "password", false, roles);
        String expected = "token";
        // when
        Mockito.when(userDetailsService.loadUserByUsername(username)).thenReturn(employee);
        Mockito.when(jwtUtil.generateToken(username, roles)).thenReturn(expected);
        String result = securityService.generateToken(username);
        // then
        assertEquals(expected, result);
        Mockito.verify(userDetailsService).loadUserByUsername(username);
    }

    @AfterEach
    void tearDown() {
    }
}