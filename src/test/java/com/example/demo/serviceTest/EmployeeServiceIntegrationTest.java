package com.example.demo.serviceTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.dto.EmployeeRequest;
import com.example.demo.dto.EmployeeResponse;
import com.example.demo.entity.Department;
import com.example.demo.entity.Employee;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.service.EmployeeService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional 
public class EmployeeServiceIntegrationTest {
	 @Autowired
	    private EmployeeService employeeService;

	    @Autowired
	    private EmployeeRepository employeeRepository;

	    @Autowired
	    private DepartmentRepository departmentRepository;

	    @Autowired
	    private ProjectRepository projectRepository;

	    @Autowired
	    private PasswordEncoder passwordEncoder;

	    private Department itDepartment;

	    @BeforeEach
	    void setUp() {
	        employeeRepository.deleteAll();
	        departmentRepository.deleteAll();
	        projectRepository.deleteAll();

	        itDepartment = Department.builder().name("IT").build();
	        departmentRepository.save(itDepartment);
	    }

	    @Test
	    void testCreateEmployee_success() {
	        EmployeeRequest request = new EmployeeRequest();
	        request.setName("Krisha");
	        request.setUsername("krisha");
	        request.setPassword("1234");
	        request.setRole("USER");
	        request.setDepartmentId(itDepartment.getId());

	        EmployeeResponse response = employeeService.createEmployee(request);

	        assertThat(response).isNotNull();
	        assertThat(response.getId()).isNotNull();
	        assertThat(response.getName()).isEqualTo("Krisha");
	        assertThat(response.getDepartment()).isEqualTo("IT");

	        Employee savedEmp = employeeRepository.findById(response.getId()).orElseThrow();
	        assertThat(savedEmp.getPassword()).isNotEqualTo("1234");  // password encoded
	        assertThat(passwordEncoder.matches("1234", savedEmp.getPassword())).isTrue();
	    }

	    @Test
	    void testGetEmployeeById_found() {
	        Employee emp = Employee.builder()
	                .name("John")
	                .username("john123")
	                .password(passwordEncoder.encode("pass"))
	                .department(itDepartment)
	                .role("USER")
	                .build();
	        emp = employeeRepository.save(emp);

	        EmployeeResponse response = employeeService.getEmployeeById(emp.getId());

	        assertThat(response).isNotNull();
	        assertThat(response.getName()).isEqualTo("John");
	        assertThat(response.getDepartment()).isEqualTo("IT");
	    }

	    @Test
	    void testGetEmployeeById_notFound() {
	        assertThatThrownBy(() -> employeeService.getEmployeeById(999))
	                .isInstanceOf(EntityNotFoundException.class)
	                .hasMessageContaining("Employee not found with id");
	    }

	    @Test
	    void testDeleteEmployee_success() {
	        Employee emp = Employee.builder()
	                .name("Asha")
	                .username("asha123")
	                .password(passwordEncoder.encode("pass"))
	                .department(itDepartment)
	                .role("USER")
	                .build();
	        emp = employeeRepository.save(emp);

	        employeeService.deleteEmployee(emp.getId());

	        assertThat(employeeRepository.findById(emp.getId())).isEmpty();
	    }
}
