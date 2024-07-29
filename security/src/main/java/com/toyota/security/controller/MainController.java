package com.toyota.security.controller;

import com.toyota.security.dto.AuthRequest;
import com.toyota.security.dto.TokenValidateDto;
import com.toyota.security.exception.InvalidJwtException;
import com.toyota.security.service.SecurityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequiredArgsConstructor
@RequestMapping("/security")
@Log4j2
public class MainController {
    private final SecurityService securityService;

    @PostMapping("/generateToken")
    public ResponseEntity<String> generateToken(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(securityService.generateToken(request));
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
