package com.toyota.security.controller;

import com.toyota.security.dto.AuthRequest;
import com.toyota.security.dto.TokenValidateDto;
import com.toyota.security.exception.InvalidUserCredentialsException;
import com.toyota.security.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;



@RestController
@RequiredArgsConstructor
@RequestMapping("/security")
public class MainController {
    private final AuthenticationManager authenticationManager;
    private final SecurityService securityService;

    @PostMapping("/generateToken")
    public ResponseEntity<String> generateToken(@RequestBody AuthRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            if (authentication.isAuthenticated()) {
                return ResponseEntity.ok(securityService.generateToken(request.getUsername()));
            } else {
                throw new InvalidUserCredentialsException("The username or password you entered is incorrect");
            }
        } catch (Exception e) {
            throw new InvalidUserCredentialsException("The username or password you entered is incorrect");
        }
    }

    @PostMapping("/validateToken")
    public Boolean validateToken(@RequestBody TokenValidateDto tokenValidateDto) {
        return securityService.validateToken(tokenValidateDto);
    }

    @GetMapping("/sales")
    public ResponseEntity<Boolean> getSales() {
        return ResponseEntity.ok(true);
    }

    @GetMapping("/campaigns")
    public ResponseEntity<Boolean> getCampaigns() {
        return ResponseEntity.ok(true);
    }

    @GetMapping("/product")
    public ResponseEntity<Boolean> getProduct() {
        return ResponseEntity.ok(true);
    }

    @GetMapping("/employee")
    public ResponseEntity<Boolean> getEmployee() {
        return ResponseEntity.ok(true);
    }

    @GetMapping("/role")
    public ResponseEntity<Boolean> getRole() {
        return ResponseEntity.ok(true);
    }

    @GetMapping("/report")
    public ResponseEntity<Boolean> getReport() {
        return ResponseEntity.ok(true);
    }


}
