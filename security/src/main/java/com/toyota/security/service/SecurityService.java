package com.toyota.security.service;

import com.toyota.entity.Employee;
import com.toyota.entity.Role;
import com.toyota.security.dto.TokenValidateDto;
import com.toyota.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class SecurityService {
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    public Boolean validateToken(TokenValidateDto token) {
        String username = jwtUtil.extractUsername(token.getToken());
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return jwtUtil.validateToken(token.getToken(), userDetails);
    }

    public String generateToken(String username) {
        Employee employee = (Employee) userDetailsService.loadUserByUsername(username);
        Set<Role> roles = employee.getRoles();
        return jwtUtil.generateToken(username, roles);
    }

}
