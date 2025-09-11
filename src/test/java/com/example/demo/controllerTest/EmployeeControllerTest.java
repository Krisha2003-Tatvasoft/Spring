package com.example.demo.controllerTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.demo.controller.EmployeeController;
import com.example.demo.dto.EmployeeRequest;
import com.example.demo.dto.EmployeeResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.service.EmployeeService;

public class EmployeeControllerTest {
	    @Mock
	    private EmployeeService service;

	    @InjectMocks
	    private EmployeeController controller;

	    @BeforeEach
	    void setUp() {
	        MockitoAnnotations.openMocks(this);
	    }

	    @Test
	    void testCreateEmployee() {
	        EmployeeRequest request = new EmployeeRequest();
	        request.setName("Krisha");
	        request.setUsername("krisha");
	        request.setPassword("1234");

	        EmployeeResponse response = new EmployeeResponse();
	        response.setId(1);
	        response.setName("Krisha");
	        when(service.createEmployee(any(EmployeeRequest.class))).thenReturn(response);

	        EmployeeResponse result = controller.createEmployee(request);

	        assertThat(result).isNotNull();
	        assertThat(result.getId()).isEqualTo(1);
	        verify(service, times(1)).createEmployee(any(EmployeeRequest.class));
	    }

	    @Test
	    void testGetAllEmployee() {
	        EmployeeResponse emp1 = new EmployeeResponse();
	        emp1.setId(1);
	        emp1.setName("Krisha");

	        EmployeeResponse emp2 = new EmployeeResponse();
	        emp2.setId(2);
	        emp2.setName("John");

	        when(service.GetAllEmployee()).thenReturn(List.of(emp1, emp2));

	        List<EmployeeResponse> result = controller.getAllEmployee();

	        assertThat(result).hasSize(2);
	        verify(service, times(1)).GetAllEmployee();
	    }

	    @Test
	    void testLogin() {
	        LoginRequest loginRequest = new LoginRequest();
	        loginRequest.setUsername("krisha");
	        loginRequest.setPassword("1234");

	        when(service.verify(any(LoginRequest.class))).thenReturn("jwt-token");

	        String result = controller.login(loginRequest);

	        assertThat(result).isEqualTo("jwt-token");
	        verify(service, times(1)).verify(any(LoginRequest.class));
	    }

	    @Test
	    void testGetEmployeeById() {
	        EmployeeResponse response = new EmployeeResponse();
	        response.setId(1);
	        response.setName("Krisha");

	        when(service.getEmployeeById(1)).thenReturn(response);

	        EmployeeResponse result = controller.getEmployeeById(1);

	        assertThat(result).isNotNull();
	        assertThat(result.getId()).isEqualTo(1);
	        verify(service, times(1)).getEmployeeById(1);
	    }

	    @Test
	    void testUpdateEmployee() {
	        EmployeeRequest request = new EmployeeRequest();
	        request.setName("UpdatedName");

	        EmployeeResponse response = new EmployeeResponse();
	        response.setId(1);
	        response.setName("UpdatedName");

	        when(service.updateEmployee(eq(1), any(EmployeeRequest.class))).thenReturn(response);

	        EmployeeResponse result = controller.updateEmployee(1, request);

	        assertThat(result).isNotNull();
	        assertThat(result.getName()).isEqualTo("UpdatedName");
	        verify(service, times(1)).updateEmployee(eq(1), any(EmployeeRequest.class));
	    }

	    @Test
	    void testDeleteEmployee() {
	        doNothing().when(service).deleteEmployee(1);

	        String result = controller.deleteEmployee(1);

	        assertThat(result).isEqualTo("Employee deleted successfully");
	        verify(service, times(1)).deleteEmployee(1);
	    }
}
