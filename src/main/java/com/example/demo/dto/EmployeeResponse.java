package com.example.demo.dto;


import java.util.List;

import com.example.demo.entity.Project;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeResponse {
	    private Integer id;
	    private String name;
	    private String department;
	    private List<Project> Projects;
}
