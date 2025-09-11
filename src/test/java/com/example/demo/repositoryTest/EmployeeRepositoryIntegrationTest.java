package com.example.demo.repositoryTest;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.example.demo.entity.Department;
import com.example.demo.entity.Employee;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.repository.EmployeeRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD) 
public class EmployeeRepositoryIntegrationTest {
	 @Autowired
	    private EmployeeRepository employeeRepository;

	    @Autowired
	    private DepartmentRepository departmentRepository;

	    private Department itDepartment;

	    @BeforeEach
	    void setUp() {
	        // Clean database before each test
	        employeeRepository.deleteAll();
	        departmentRepository.deleteAll();

	        // Create a department
	        itDepartment = Department.builder().name("IT").build();
	        departmentRepository.save(itDepartment);
	    }

	    @Test
	    void testFindByUsername_found() {
	        // Arrange
	        Employee emp = Employee.builder()
	                .username("krisha")
	                .password("Krisha@123")
	                .name("Krisha V")
	                .department(itDepartment)
	                .build();
	        employeeRepository.save(emp);

	        // Act
	        Employee found = employeeRepository.findByUsername("krisha");

	        // Assert
	        assertThat(found).isNotNull();
	        assertThat(found.getName()).isEqualTo("Krisha V");
	        assertThat(found.getDepartment().getName()).isEqualTo("IT");
	    }

	    @Test
	    void testFindByUsername_notFound() {
	        // Act
	        Employee found = employeeRepository.findByUsername("unknown");

	        // Assert
	        assertThat(found).isNull();
	    }

}
