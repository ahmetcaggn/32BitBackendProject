package com.toyota.security.controller;

import com.toyota.security.dto.AuthRequest;
import com.toyota.security.dto.TokenValidateDto;
import com.toyota.security.service.SecurityService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class MainControllerTest {

    private SecurityService securityService;
    private MainController mainController;

    @BeforeEach
    void setUp() {
        securityService = Mockito.mock(SecurityService.class);
        mainController = new MainController(securityService);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void shouldReturnTrue_WhenValidateTokenMethodCallsWithGivenToken() {
        // given
        String token = "token";
        TokenValidateDto tokenValidateDto = new TokenValidateDto(token);
        // when
        Mockito.when(securityService.validateToken(tokenValidateDto)).thenReturn(true);
        Boolean result = mainController.validateToken(tokenValidateDto);
        // then
        assertTrue(result);
        Mockito.verify(securityService).validateToken(tokenValidateDto);
    }

    @Test
    void shouldReturnToken_WhenValidateTokenMethodCallsWithGivenRequest() {
        // given
        AuthRequest request = new AuthRequest("username", "password");
        String token = "token";
        // when
        Mockito.when(securityService.generateToken(request)).thenReturn(token);
        String result = mainController.generateToken(request).getBody();
        // then
        assertEquals(token, result);
        Mockito.verify(securityService).generateToken(request);
    }

    @Test
    void shouldReturnTrue_WhenGetSalesMethodCalls() {
        // given
        // when
        Boolean result = mainController.getSales().getBody();
        // then
        assertEquals(Boolean.TRUE, result);
    }

    @Test
    void shouldReturnTrue_WhenGetCampaignsMethodCalls() {
        // given
        // when
        Boolean result = mainController.getCampaigns().getBody();
        // then
        assertEquals(Boolean.TRUE, result);
    }

    @Test
    void shouldReturnTrue_WhenGetProductMethodCalls() {
        // given
        // when
        Boolean result = mainController.getProduct().getBody();
        // then
        assertEquals(Boolean.TRUE, result);
    }

    @Test
    void shouldReturnTrue_WhenGetEmployeeMethodCalls() {
        // given
        // when
        Boolean result = mainController.getEmployee().getBody();
        // then
        assertEquals(Boolean.TRUE, result);
    }

    @Test
    void shouldReturnTrue_WhenGetRoleMethodCalls() {
        // given
        // when
        Boolean result = mainController.getRole().getBody();
        // then
        assertEquals(Boolean.TRUE, result);
    }

    @Test
    void shouldReturnTrue_WhenGetReportMethodCalls() {
        // given
        // when
        Boolean result = mainController.getReport().getBody();
        // then
        assertEquals(Boolean.TRUE, result);
    }

}