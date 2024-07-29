package com.toyota.security.service;

import com.toyota.entity.Employee;
import com.toyota.entity.Role;
import com.toyota.security.dto.AuthRequest;
import com.toyota.security.dto.TokenValidateDto;
import com.toyota.security.exception.InvalidUserCredentialsException;
import com.toyota.security.util.JwtUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


class SecurityServiceTest {

    private SecurityService securityService;
    private JwtUtil jwtUtil;
    private CustomUserDetailsService userDetailsService;
    private AuthenticationManager authenticationManager;


    @BeforeEach
    void setUp() {
        jwtUtil = Mockito.mock(JwtUtil.class);
        userDetailsService = Mockito.mock(CustomUserDetailsService.class);
        authenticationManager = Mockito.mock(AuthenticationManager.class);
        securityService = new SecurityService(jwtUtil, userDetailsService, authenticationManager);
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
    void validateToken_InvalidToken() {
         
    }
//
//    @Test
//    void generateToken() {
//        // given
//        String username = "username";
//        Set<Role> roles = Set.of(Role.ADMIN, Role.CASHIER, Role.MANAGER);
//        Employee employee = new Employee(1L, "name", "surname", "address", "phoneNo", username, "password", false, roles);
//        String expected = "token";
//        // when
//        Mockito.when(userDetailsService.loadUserByUsername(username)).thenReturn(employee);
//        Mockito.when(jwtUtil.generateToken(username, roles)).thenReturn(expected);
//        String result = securityService.generateToken(username);
//        // then
//        assertEquals(expected, result);
//        Mockito.verify(userDetailsService).loadUserByUsername(username);
//    }

    @Test
    void shouldThrowInvalidUserCredentialsException_WhenGenerateTokenWithInvalidUserCredentials() {
        // given
        String username = "username";
        String password = "password";
        // when
        Mockito.when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password))).thenThrow(new InvalidUserCredentialsException("The username or password you entered is incorrect"));
        // then
        assertThrows(InvalidUserCredentialsException.class, () -> securityService.generateToken(new AuthRequest(username, password)));
        Mockito.verify(authenticationManager).authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

//    @Test
//    void shouldThrowInvalidUserCredentialsException_WhenGenerateTokenWithAuthenticationNotAuthenticated() {
//        // given
//        String username = "username";
//        String password = "password";
//        // when
//        Mockito.when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password))).thenReturn(new UsernamePasswordAuthenticationToken(username, password));
//        // then
//        assertThrows(InvalidUserCredentialsException.class, () -> securityService.generateToken(new AuthRequest(username, password)));
//        Mockito.verify(authenticationManager).authenticate(new UsernamePasswordAuthenticationToken(username, password));
//    }

    @Test
    void shouldReturnToken_WhenGenerateTokenWithValidUserCredentials() {
        // given
        String username = "username";
        String password = "password";
        Set<Role> roles = Set.of(Role.ADMIN, Role.CASHIER, Role.MANAGER);
        Employee employee = new Employee(1L, "name", "surname", "address", "phoneNo", username, "password", false, roles);
        String token = "token";
        // when
        Mockito.when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password))).thenReturn(new UsernamePasswordAuthenticationToken(username, password));
        Mockito.when(userDetailsService.loadUserByUsername(username)).thenReturn(employee);
        Mockito.when(jwtUtil.generateToken(username, roles)).thenReturn(token);
        String result = securityService.generateToken(new AuthRequest(username, password));
        // then
        assertEquals(token, result);
        Mockito.verify(authenticationManager).authenticate(new UsernamePasswordAuthenticationToken(username, password));
        Mockito.verify(userDetailsService).loadUserByUsername(username);
        Mockito.verify(jwtUtil).generateToken(username, roles);
    }

    @AfterEach
    void tearDown() {
    }
}