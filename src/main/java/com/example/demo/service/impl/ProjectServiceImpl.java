package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.ProjectRequest;
import com.example.demo.entity.Employee;
import com.example.demo.entity.Project;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.service.ProjectService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
	
	 private final ProjectRepository projectRepository;
	 
	 private final EmployeeRepository employeeRepository;

	    @Override
	    public Project createProject(ProjectRequest  request) {
	    	  List<Employee> employees = new ArrayList<>();
	    	    
	    	  if (request.getEmployeeIds() != null && !request.getEmployeeIds().isEmpty()) {
	    	        employees = request.getEmployeeIds().stream()
	    	            .map(id -> employeeRepository.findById(id)
	    	                .orElseThrow(() -> new EntityNotFoundException("Employee not found: " + id)))
	    	            .toList();
	    	    }


	    	  Project project = Project.builder()
	    	            .name(request.getName())
	    	            .employees(employees)
	    	            .build();
	    	  
// Important: Set the reverse relationship
// add project in employee...we have mapped by in project so we have to  here so we have to add here..
// owning side s employee so or updating a mapping table we have to save and update the owing table...
	    	    employees.forEach(emp -> emp.getProjects().add(project));
	    	    
	    	    
	    	    return projectRepository.save(project);
	    }

	    @Override
	    public List<Project> getAllProjects() {
	        return projectRepository.findAll();
	    }
}
