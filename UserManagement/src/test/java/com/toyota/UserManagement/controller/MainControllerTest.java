package com.toyota.UserManagement.controller;

import com.toyota.UserManagement.dto.EmployeeDto;
import com.toyota.UserManagement.dto.EmployeeDtoWithPwd;
import com.toyota.UserManagement.dto.EmployeeRequestDto;
import com.toyota.UserManagement.service.UserManagementService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MainControllerTest {

    public UserManagementService userManagementService;
    private MainController mainController;

    @BeforeEach
    void setUp() {
        userManagementService = Mockito.mock(UserManagementService.class);
        mainController = new MainController(userManagementService);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void shouldReturnEmployeeDtoList_WhenFetchAllEmployeesMethodCalls() {

        // given
        EmployeeDto employeeDto1 = new EmployeeDto(1L, "name1", "surname1", "address1", "phoneNo1", "username1", new HashSet<>());
        EmployeeDto employeeDto2 = new EmployeeDto(2L, "name2", "surname2", "address2", "phoneNo2", "username2", new HashSet<>());
        // when
        Mockito.when(userManagementService.getAllEmployees()).thenReturn(List.of(employeeDto1, employeeDto2));
        List<EmployeeDto> result = mainController.fetchAllEmployees();

        // then
        assertEquals(List.of(employeeDto1, employeeDto2), result);
        Mockito.verify(userManagementService).getAllEmployees();
    }

    @Test
    void shouldReturnEmployeeDtoWithPwd_WhenFetchEmployeeByUsername() {

        // given
        String username = "username";
        EmployeeDtoWithPwd employeeDtoWithPwd = new EmployeeDtoWithPwd(1L, "name", "surname", "address", "phoneNo", username, "password", new HashSet<>());
        // when
        Mockito.when(userManagementService.getEmployeeByUsername(username)).thenReturn(employeeDtoWithPwd);
        EmployeeDtoWithPwd result = mainController.fetchEmployeeByUsername(username);

        // then
        assertEquals(employeeDtoWithPwd, result);
        Mockito.verify(userManagementService).getEmployeeByUsername(username);
    }

    @Test
    void shouldReturnEmployeeDtoWithGivenRequestDto_WhenSaveEmployeeMethodCalled() {

            // given
            EmployeeRequestDto employeeRequestDto = new EmployeeRequestDto("name", "surname", "phoneNo", "username","address","", new HashSet<>());
            EmployeeDto employeeDto = new EmployeeDto(1L, "name", "surname", "address", "phoneNo", "username", new HashSet<>());
            // when
            Mockito.when(userManagementService.saveEmployee(employeeRequestDto)).thenReturn(employeeDto);
            EmployeeDto result = mainController.saveEmployee(employeeRequestDto);

            // then
            assertEquals(employeeDto, result);
            Mockito.verify(userManagementService).saveEmployee(employeeRequestDto);
    }

    @Test
    void shouldReturnEmployeeDtoWithGivenRequestDto_WhenUpdateEmployeeMethodCalled() {

        // given
        Long employeeId = 1L;
        EmployeeRequestDto employeeRequestDto = new EmployeeRequestDto("name", "surname", "phoneNo", "username","address","", new HashSet<>());
        EmployeeDto employeeDto = new EmployeeDto(employeeId, "name", "surname", "address", "phoneNo", "username", new HashSet<>());
        // when
        Mockito.when(userManagementService.updateEmployeeById(employeeId, employeeRequestDto)).thenReturn(employeeDto);
        EmployeeDto result = mainController.updateEmployee(employeeId, employeeRequestDto);

        // then
        assertEquals(employeeDto, result);
        Mockito.verify(userManagementService).updateEmployeeById(employeeId, employeeRequestDto);
    }

    @Test
    void shouldReturnTrue_WhenEmployeeDeletedSuccessfullyWithGivenId() {

            // given
            Long employeeId = 1L;
            // when
            Mockito.when(userManagementService.deleteEmployeeById(employeeId)).thenReturn(true);
            Boolean result = mainController.deleteEmployee(employeeId);

            // then
            assertTrue(result);
            Mockito.verify(userManagementService).deleteEmployeeById(employeeId);
    }
}