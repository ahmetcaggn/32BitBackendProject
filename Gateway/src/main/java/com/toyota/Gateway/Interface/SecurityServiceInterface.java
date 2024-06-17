package com.toyota.Gateway.Interface;

import com.toyota.Gateway.dto.TokenValidateDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "SECURITY")
public interface SecurityServiceInterface {

    @PostMapping("/validateToken")
    public Boolean validateToken(@RequestBody TokenValidateDto tokenValidateDto);

    @GetMapping("/sales")
    public ResponseEntity<Boolean> getSales();

    @GetMapping("/campaigns")
    public ResponseEntity<Boolean> getCampaigns();

    @GetMapping("/product")
    public ResponseEntity<Boolean> getProduct();

    @GetMapping("/employee")
    public ResponseEntity<Boolean> getEmployee();

    @GetMapping("/role")
    public ResponseEntity<Boolean> getRole();

    @GetMapping("/report")
    public ResponseEntity<Boolean> getReport();
}
