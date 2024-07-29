package com.toyota.security.service;

import com.toyota.entity.Employee;
import com.toyota.entity.Role;
import com.toyota.security.dto.AuthRequest;
import com.toyota.security.dto.TokenValidateDto;
import com.toyota.security.exception.InvalidJwtException;
import com.toyota.security.exception.InvalidUserCredentialsException;
import com.toyota.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Log4j2
public class SecurityService {
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;


    public Boolean validateToken(TokenValidateDto token) {
        try {
            String username = jwtUtil.extractUsername(token.getToken());
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            Boolean result = jwtUtil.validateToken(token.getToken(), userDetails);
            log.info("The jwt token is valid");
            return result;
        } catch (Exception e) {
            throw new InvalidJwtException("Invalid Jwt Token");
        }
    }

    public String generateToken(AuthRequest request) {

        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

            if (authentication.isAuthenticated()) {
                Employee employee = (Employee) userDetailsService.loadUserByUsername(request.getUsername());
                Set<Role> roles = employee.getRoles();

                String token = jwtUtil.generateToken(request.getUsername(), roles);
                log.info("Token is created successfully by user {}", request.getUsername());
                return token;
            } else {
                throw new InvalidUserCredentialsException("The username or password you entered is incorrect");
            }
        } catch (Exception e) {
            throw new InvalidUserCredentialsException("The username or password you entered is incorrect");
        }

    }

}
