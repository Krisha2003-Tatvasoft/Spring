package com.example.demo.repositoryTest;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.demo.entity.Employee;
import com.example.demo.repository.EmployeeRepository;



@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EmployeeRepositoryTest {
	
	@Autowired
    private EmployeeRepository employeeRepository;
	
	@Test
    void testFindByUsername_found() {
        // Arrange (insert test data in H2)
        Employee emp = new Employee();
        emp.setUsername("krisha");
        emp.setPassword("Krisha@123");
        emp.setName("Krisha V");
        emp.setDepartment(null);
        employeeRepository.save(emp);

        // Act
        Employee found = employeeRepository.findByUsername("krisha");

        // Assert
        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo("Krisha V");
        assertThat(found.getDepartment()).isEqualTo("IT");
    }

    @Test
    void testFindByUsername_notFound() {
        // Act
        Employee found = employeeRepository.findByUsername("unknown");

        // Assert
        assertThat(found).isNull(); // repository method returns null if no record
    }

}
