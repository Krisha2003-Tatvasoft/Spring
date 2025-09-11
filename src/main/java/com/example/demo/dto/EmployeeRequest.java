package com.example.demo.dto;

import java.util.List;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeRequest {
	 private String name;
	 private Integer departmentId; 
	 private List<Integer> projectId;
	 private String username;
	    private String password;
	    private String role; // e.g., ROLE_USER, ROLE_ADMIN
}
