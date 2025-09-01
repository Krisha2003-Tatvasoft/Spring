package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.EmployeeRequest;
import com.example.demo.dto.EmployeeResponse;
import com.example.demo.entity.Department;
import com.example.demo.entity.Employee;
import com.example.demo.entity.Project;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.service.EmployeeService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
	
	private final EmployeeRepository emprepo;
	
	private final DepartmentRepository departmentRepository;
	
	private final ProjectRepository projectRepository;
	
	 @Override
	 public EmployeeResponse createEmployee(EmployeeRequest request)
	 {
		 
		  Department dept = departmentRepository.findById(request.getDepartmentId())
	                .orElseThrow(() -> new EntityNotFoundException("Department not found"));
		  
//		  so at owning side u do not need to add at inverse side manualy.....
//		  so here u do not need to add employee in project...
//		  like this if u add from the inverse side u have to add to owning side too...
		  
		  List<Project> projects = new ArrayList<>();
		    if (request.getProjectId() != null && !request.getProjectId().isEmpty()) {
		        projects = request.getProjectId().stream()
		                .map(id -> projectRepository.findById(id)
		                        .orElseThrow(() -> new EntityNotFoundException("Project not found: " + id)))
		                .toList();
		    }
		  
		  
	        Employee emp = Employee.builder()
	                .name(request.getName())
	                .department(dept)
	                .projects(projects)
	                .build();

	        Employee created = emprepo.save(emp);
	        return mapToResponse(created);
     }

	 @Override
	 public List<EmployeeResponse> GetAllEmployee()
	 {
		  return emprepo.findAll().stream()
	                .map(this::mapToResponse)
	                .toList();
     }
	 
	 @Override
	 public EmployeeResponse getEmployeeById(Integer id)
	 {
		 Employee emp = emprepo.findById(id)
				       .orElseThrow(()-> new EntityNotFoundException("Employee not found with id: " + id));
		 return mapToResponse(emp);
     }
	 
	 @Override
	    public EmployeeResponse updateEmployee(Integer id, EmployeeRequest request) {
		 
		 Employee emp = emprepo.findById(id)
			       .orElseThrow(()-> new EntityNotFoundException("Employee not found with id: " + id));

		   Department dept = departmentRepository.findById(request.getDepartmentId())
	                .orElseThrow(() -> new EntityNotFoundException("Department not found"));

		   List<Project> projects = new ArrayList<>();
		    if (request.getProjectId() != null && !request.getProjectId().isEmpty()) {
		        projects = request.getProjectId().stream()
		                .map(pid -> projectRepository.findById(pid)
		                        .orElseThrow(() -> new EntityNotFoundException("Project not found: " + pid)))
		                .toList();
		    }

		    // 4. Update fields
		    emp.setName(request.getName());
		    emp.setDepartment(dept);

		    // 5. Update projects (owning side)..we change will it will update in mapping table
		    emp.getProjects().clear();            // remove old mappings
		    emp.getProjects().addAll(projects);   // add new ones


	        Employee updated = emprepo.save(emp);
	        return mapToResponse(updated);
	    }

	    @Override
	    public void deleteEmployee(Integer id) {
	    	 Employee emp = emprepo.findById(id)
				       .orElseThrow(()-> new EntityNotFoundException("Employee not found with id: " + id));
	    	 

	    	 emprepo.delete(emp);
	    }
	    
	    
//	    public List<EmployeeResponse> getEmployeesByDepartment(String department) {
//	        List<Employee> employees = emprepo.findByDepartment(department);
//	        if (employees.isEmpty()) {
//	            throw new EntityNotFoundException("No employees found in department: " + department);
//	        }
//	        return employees.stream()
//	        		.map(this::mapToResponse)
//	        		.toList();
//	    }
//
//	    public List<EmployeeResponse> searchEmployeesByName(String keyword) {
//	        List<Employee> employees = emprepo.findByNameContains(keyword);
//	        if (employees.isEmpty()) {
//	            throw new EntityNotFoundException("No employees found with name containing: " + keyword);
//	        }
//	        return employees.stream()
//	        		.map(this::mapToResponse)
//	        		.toList();
//	    }
//
//	    public List<EmployeeResponse> getEmployeesByDepartmentSorted(String department) {
//	        List<Employee> employees = emprepo.findByDepartmentSortedByName(department);
//	        if (employees.isEmpty()) {
//	            throw new EntityNotFoundException("No employees found in department: " + department);
//	        }
//	        return employees.stream()
//	        		.map(this::mapToResponse)
//	        		.toList();
//	    }
//	    
//	    

	 private EmployeeResponse mapToResponse(Employee emp) {
	        return EmployeeResponse.builder()
	                .id(emp.getId())
	                .name(emp.getName())
	                .department(emp.getDepartment().getName())
	                .Projects(emp.getProjects())
	                .build();
	    }
	 
	 
	 
	 
}
