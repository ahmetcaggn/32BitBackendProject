package com.toyota.security.controller;

import com.toyota.security.dto.AuthRequest;
import com.toyota.security.dto.CreateUserRequest;
import com.toyota.security.entity.Users;
import com.toyota.security.service.CustomUserDetailsService;
import com.toyota.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MainController {
    private final CustomUserDetailsService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping("/addNewUser")
    public Users addUser(@RequestBody CreateUserRequest request) {
        return userService.createUser(request);
    }

    @PostMapping("/generateToken")
    public String generateToken(@RequestBody AuthRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            if (authentication.isAuthenticated()) {
                return jwtUtil.generateToken(request.getUsername());
            }
        } catch (Exception e) {
            throw new UsernameNotFoundException("invalid username {} " + request.getUsername());
        }
        return null;
    }

    @GetMapping("/manager")
    public String getManagerString() {
        return "This is MANAGER!";
    }

    @GetMapping("/admin")
    public String getAdminString() {
        return "This is ADMIN!";
    }

    @GetMapping("/cashier")
    public String getCashierString() {
        return "This is CASHIER!";
    }
}
