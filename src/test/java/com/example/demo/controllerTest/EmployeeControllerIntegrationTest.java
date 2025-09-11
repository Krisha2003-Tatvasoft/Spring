package com.example.demo.controllerTest;

import com.example.demo.config.TestSecurityConfig;
import com.example.demo.dto.EmployeeRequest;
import com.example.demo.entity.Department;
import com.example.demo.entity.Employee;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.repository.EmployeeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestSecurityConfig.class)   // Disable normal security for tests
public class EmployeeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    private Department itDepartment;

    @BeforeEach
    void setUp() {
        employeeRepository.deleteAllInBatch();
        departmentRepository.deleteAllInBatch();

        itDepartment = Department.builder().name("IT").build();
        departmentRepository.save(itDepartment);
    }

    @Test
    void testCreateEmployee() throws Exception {
        EmployeeRequest request = new EmployeeRequest();
        request.setName("Krisha");
        request.setUsername("krisha");
        request.setPassword("1234");
        request.setRole("USER");
        request.setDepartmentId(itDepartment.getId());

        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Krisha"))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.department").value("IT"));
    }

    @Test
    void testGetEmployeeById() throws Exception {
        Employee emp = Employee.builder()
                .name("John")
                .username("john123")
                .password("pass")
                .department(itDepartment)
                .build();
        emp = employeeRepository.save(emp);

        mockMvc.perform(get("/" + emp.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.id").value(emp.getId()))
                .andExpect(jsonPath("$.department").value("IT"));
    }

    @Test
    void testUpdateEmployee() throws Exception {
        Employee emp = Employee.builder()
                .name("Old Name")
                .username("old123")
                .password("pass")
                .department(itDepartment)
                .build();
        emp = employeeRepository.save(emp);

        EmployeeRequest updateRequest = new EmployeeRequest();
        updateRequest.setName("New Name");
        updateRequest.setUsername("old123");
        updateRequest.setPassword("pass");
        updateRequest.setRole("USER");
        updateRequest.setDepartmentId(itDepartment.getId());

        mockMvc.perform(put("/" + emp.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New Name"))
                .andExpect(jsonPath("$.department").value("IT"));
    }

    @Test
    void testDeleteEmployee() throws Exception {
        Employee emp = Employee.builder()
                .name("ToDelete")
                .username("delete123")
                .password("pass")
                .department(itDepartment)
                .build();
        emp = employeeRepository.save(emp);

        mockMvc.perform(delete("/" + emp.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string("Employee deleted successfully"));
    }
}
