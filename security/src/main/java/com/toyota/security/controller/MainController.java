package com.toyota.security.controller;

import com.toyota.entity.Employee;
import com.toyota.security.dto.AuthRequest;
import com.toyota.security.dto.EmployeeDto;
import com.toyota.security.dto.TokenValidateDto;
import com.toyota.security.interfaces.UserManagementInterface;
import com.toyota.security.service.SecurityService;
import com.toyota.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/security")
public class MainController {
    private final AuthenticationManager authenticationManager;
    private final SecurityService securityService;
    private final JwtUtil jwtUtil;
    private final UserManagementInterface userManagementInterface;
    private final RestTemplate restTemplate;

    @PostMapping("/generateToken")
    public ResponseEntity<String> generateToken(@RequestBody AuthRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            if (authentication.isAuthenticated()) {
                return ResponseEntity.ok(securityService.generateToken(request.getUsername()));
            }
            else {
                return ResponseEntity.badRequest().body("BASARAMADIK");
            }
        } catch (Exception e) {
            throw new UsernameNotFoundException("invalid username {} " + request.getUsername());
        }
    }

//    @PostMapping("/validateToken")
//    public Boolean validateToken(@RequestBody TokenValidateDto tokenValidateDto){
//        return securityService.validateToken(tokenValidateDto);
//    }
    @PostMapping("/validateToken")
    public Boolean validateToken(@RequestParam("token") String token){
        return securityService.validateToken(token);
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
