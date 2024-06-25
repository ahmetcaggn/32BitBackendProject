package com.toyota.UserManagement.service;

import com.toyota.UserManagement.Exception.EmployeeNotFoundException;
import com.toyota.UserManagement.dto.CreateUserRequest;
import com.toyota.UserManagement.dto.EmployeeDto;
import com.toyota.UserManagement.dto.EmployeeRequest;
import com.toyota.UserManagement.repository.EmployeeRepository;
import com.toyota.entity.Employee;
import com.toyota.entity.Sale;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.jdbc.SqlConfig;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserManagementServiceTest {
    private UserManagementService userManagementService;
    private PasswordEncoder passwordEncoder;
    private EmployeeRepository employeeRepository;

    @BeforeEach
    void setUp() {
        employeeRepository = Mockito.mock(EmployeeRepository.class);
        passwordEncoder = Mockito.mock(PasswordEncoder.class);
        userManagementService = new UserManagementService(passwordEncoder, employeeRepository);
    }

    @DisplayName("getAllEmployees() method should return EmployeeDto list when employees exist")
    @Test
    public void shouldReturnEmployeeDtoList_WhenEmployeesExist() {
        // given
        Employee employee1 = new Employee(1L, "name1", "surname1", "username1", "password1", "address1", "phoneNo1", false, new HashSet<>());
        Employee employee2 = new Employee(2L, "name1", "surname1", "username1", "password1", "address1", "phoneNo1", false, new HashSet<>());
        List<Employee> employeeList = List.of(employee1, employee2);
        List<EmployeeDto> expected = List.of(new EmployeeDto(employee1), new EmployeeDto(employee2));
        // when
        Mockito.when(employeeRepository.findAllByIsDeletedFalse()).thenReturn(employeeList);
        List<EmployeeDto> result = userManagementService.getAllEmployees();
        // then
        assertArrayEquals(expected.toArray(), result.toArray());
        Mockito.verify(employeeRepository).findAllByIsDeletedFalse();


    }

    @DisplayName("getEmployeeByUsername() method should return EmployeeDto when employee exist")
    @Test
    public void shouldReturnEmployeeDto_WhenEmployeeExist() {
        // given
        Employee employee = new Employee(1L, "name", "surname", "address", "phoneNo", "username", "password", false, new HashSet<>());
        EmployeeDto expected = new EmployeeDto(employee);
        // when
        Mockito.when(employeeRepository.findByUsername(employee.getUsername())).thenReturn(Optional.of(employee));
        EmployeeDto result = userManagementService.getEmployeeByUsername(employee.getUsername());
        // then
        assertEquals(expected, result);
        Mockito.verify(employeeRepository).findByUsername(employee.getUsername());

    }

    @DisplayName("getEmployeeByUsername() method should EmployeeNotFoundException when employee does not exist")
    @Test
    public void shouldThrowEmployeeNotFoundException_WhenEmployeeDoesNotExist() {
        // given
        Employee employee = new Employee(1L, "name", "surname", "address", "phoneNo", "username", "password", false, new HashSet<>());
        // when
        Mockito.when(employeeRepository.findByUsername(employee.getUsername())).thenReturn(Optional.empty());
        // then
        assertThrows(EmployeeNotFoundException.class, () -> userManagementService.getEmployeeByUsername(employee.getUsername()));
        Mockito.verify(employeeRepository).findByUsername(employee.getUsername());
    }

    @DisplayName("updateEmployeeById() method should return EmployeeDto when employee exist")
    @Test
    void shouldReturnEmployeeAndUpdateEmployeeInformation_WhenGivenIdIsExist() {
        // given
        Employee employee = new Employee(1L, "name", "surname", "address", "phoneNo", "username", "password", false, new HashSet<>());
        EmployeeRequest updatedEmployeeRequest = new EmployeeRequest("John", "surname1","username", "address", "address","phoneNo",new HashSet<>());
        employee.setName(updatedEmployeeRequest.getName());
        employee.setSurname(updatedEmployeeRequest.getSurname());
        employee.setUsername(updatedEmployeeRequest.getUsername());
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        employee.setAddress(updatedEmployeeRequest.getAddress());
        employee.setPhoneNo(updatedEmployeeRequest.getPhoneNo());
        employee.setRoles(updatedEmployeeRequest.getRoles());

        EmployeeDto expected = new EmployeeDto(employee);
        // when
        Mockito.when(employeeRepository.findByIsDeletedFalseAndId(employee.getId())).thenReturn(Optional.of(employee));
        Mockito.when(employeeRepository.save(employee)).thenReturn(employee);
        EmployeeDto result = userManagementService.updateEmployeeById(employee.getId(), updatedEmployeeRequest);
        // then
        assertEquals(expected, result);
        Mockito.verify(employeeRepository).findByIsDeletedFalseAndId(employee.getId());
        Mockito.verify(employeeRepository).save(employee);
    }

    @DisplayName("updateEmployeeById() method should throw EmployeeNotFoundException when employee does not exist")
    @Test
    void shouldThrowEmployeeNotFoundException_WhenGivenIdIsNotExist() {
        // given
        Long employeeId = 1L;
        EmployeeRequest updatedEmployeeRequest = new EmployeeRequest("John", "surname1","username", "address", "address","phoneNo",new HashSet<>());
        // when
        Mockito.when(employeeRepository.findByIsDeletedFalseAndId(employeeId)).thenReturn(Optional.empty());
        // then
        assertThrows(EmployeeNotFoundException.class, () -> userManagementService.updateEmployeeById(employeeId, updatedEmployeeRequest));
        Mockito.verify(employeeRepository).findByIsDeletedFalseAndId(employeeId);
    }

    @DisplayName("saveEmployee() method should return EmployeeDto when employee is saved")
    @Test
    void shouldReturnEmployeeDto_WhenGivenEmployeeRequestDtoIsValid() {
        // given
        CreateUserRequest employeeRequest = new CreateUserRequest("name", "surname", "address", "phoneNo", "username", "password", new HashSet<>());
        Employee employee = Employee.builder()
                .name(employeeRequest.getName())
                .surname(employeeRequest.getSurname())
                .address(employeeRequest.getAddress())
                .phoneNo(employeeRequest.getPhoneNo())
                .username(employeeRequest.getUsername())
                .isDeleted(false)
                .password(passwordEncoder.encode(employeeRequest.getPassword()))
                .roles(employeeRequest.getRoles())
                .build();
        Employee savedEmployee = Employee.builder()
                .id(1L)
                .name(employeeRequest.getName())
                .surname(employeeRequest.getSurname())
                .address(employeeRequest.getAddress())
                .phoneNo(employeeRequest.getPhoneNo())
                .username(employeeRequest.getUsername())
                .isDeleted(false)
                .password(passwordEncoder.encode(employeeRequest.getPassword()))
                .roles(employeeRequest.getRoles())
                .build();
        EmployeeDto expected = new EmployeeDto(savedEmployee);
        // when
        Mockito.when(employeeRepository.save(Mockito.any())).thenReturn(savedEmployee);
        EmployeeDto result = userManagementService.saveEmployee(employeeRequest);
        // then
        assertEquals(expected, result);
        Mockito.verify(employeeRepository).save(employee);
    }

    @Test
    void shouldReturnTrue_WhenEmployeeDeletedWithGivenIdThatIsValid() {
        // given
        Long employeeId = 1L;
        Employee employee = new Employee(1L, "name", "surname", "address", "phoneNo", "username", "password", false, new HashSet<>());
        Employee employeeDeleted = new Employee(1L, "name", "surname", "address", "phoneNo", "username", "password", true, new HashSet<>());
        // when
        Mockito.when(employeeRepository.findByIsDeletedFalseAndId(employeeId)).thenReturn(Optional.of(employee));
        Mockito.when(employeeRepository.save(employee)).thenReturn(employeeDeleted);
        Boolean result = userManagementService.deleteEmployeeById(employeeId);
        // then
        assertTrue(result);
        Mockito.verify(employeeRepository).findByIsDeletedFalseAndId(employeeId);
        Mockito.verify(employeeRepository).save(employeeDeleted);
    }

    @Test
    void shouldThrowException_WhenEmployeeDoesNotExistWithGivenId() {
        // given
        Long employeeId = 1L;
        // when
        Mockito.when(employeeRepository.findByIsDeletedFalseAndId(employeeId)).thenReturn(Optional.empty());
        // then
        assertThrows(EmployeeNotFoundException.class, () -> userManagementService.deleteEmployeeById(employeeId));
        Mockito.verify(employeeRepository).findByIsDeletedFalseAndId(employeeId);
    }

    @AfterEach
    void tearDown() {
    }
}