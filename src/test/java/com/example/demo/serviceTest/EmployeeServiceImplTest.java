package com.example.demo.serviceTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.dto.EmployeeRequest;
import com.example.demo.dto.EmployeeResponse;

import com.example.demo.entity.Department;
import com.example.demo.entity.Employee;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.service.impl.EmployeeServiceImpl;

import jakarta.persistence.EntityNotFoundException;

public class EmployeeServiceImplTest {
	    @Mock
	    private EmployeeRepository employeeRepository;

	    @Mock
	    private DepartmentRepository departmentRepository;

	    @Mock
	    private ProjectRepository projectRepository;

	    @Mock
	    private PasswordEncoder passwordEncoder;

	    @Mock
	    private AuthenticationManager authManager;


	    @InjectMocks
	    private EmployeeServiceImpl employeeService;

	    @BeforeEach
	    void setUp() {
	        MockitoAnnotations.openMocks(this);
	    }

	    @Test
	    void testCreateEmployee_success() {
	        // Arrange
	        EmployeeRequest request = new EmployeeRequest();
	        request.setName("Krisha");
	        request.setUsername("krisha");
	        request.setPassword("1234");
	        request.setRole("USER");
	        request.setDepartmentId(1);

	        Department dept = Department.builder().id(1).name("IT").build();

	        when(departmentRepository.findById(1)).thenReturn(Optional.of(dept));
	        when(passwordEncoder.encode("1234")).thenReturn("encodedPwd");

	        Employee savedEmp = Employee.builder()
	                .id(10)
	                .name("Krisha")
	                .username("krisha")
	                .password("encodedPwd")
	                .department(dept)
	                .role("USER")
	                .projects(List.of())
	                .build();

	        when(employeeRepository.save(any(Employee.class))).thenReturn(savedEmp);

	        // Act
	        EmployeeResponse response = employeeService.createEmployee(request);

	        // Assert
	        assertThat(response).isNotNull();
	        assertThat(response.getId()).isEqualTo(10);
	        assertThat(response.getName()).isEqualTo("Krisha");
	        assertThat(response.getDepartment()).isEqualTo("IT");

	        verify(employeeRepository, times(1)).save(any(Employee.class));
	    }

	    @Test
	    void testGetEmployeeById_found() {
	        // Arrange
	        Department dept = Department.builder().id(1).name("HR").build();
	        Employee emp = Employee.builder().id(5).name("John").department(dept).build();

	        when(employeeRepository.findById(5)).thenReturn(Optional.of(emp));

	        // Act
	        EmployeeResponse response = employeeService.getEmployeeById(5);

	        // Assert
	        assertThat(response).isNotNull();
	        assertThat(response.getName()).isEqualTo("John");
	        assertThat(response.getDepartment()).isEqualTo("HR");
	    }

	    @Test
	    void testGetEmployeeById_notFound() {
	        when(employeeRepository.findById(99)).thenReturn(Optional.empty());

	        assertThatThrownBy(() -> employeeService.getEmployeeById(99))
	                .isInstanceOf(EntityNotFoundException.class)
	                .hasMessageContaining("Employee not found with id");
	    }

	    @Test
	    void testDeleteEmployee_success() {
	        Department dept = Department.builder().id(1).name("Finance").build();
	        Employee emp = Employee.builder().id(7).name("Asha").department(dept).build();

	        when(employeeRepository.findById(7)).thenReturn(Optional.of(emp));

	        // Act
	        employeeService.deleteEmployee(7);

	        // Assert
	        verify(employeeRepository, times(1)).delete(emp);
	    }

	   
}
