package com.example.demo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.EmployeeRequest;
import com.example.demo.dto.EmployeeResponse;
import com.example.demo.entity.Employee;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.service.EmployeeService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
	
	private final EmployeeRepository emprepo;
	
	 @Override
	 public EmployeeResponse createEmployee(EmployeeRequest request)
	 {
		Employee emp = Employee.builder()
		               .name(request.getName())
		               .department(request.getDepartment())
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

	        emp.setName(request.getName());
	        emp.setDepartment(request.getDepartment());

	        Employee updated = emprepo.save(emp);
	        return mapToResponse(updated);
	    }

	    @Override
	    public void deleteEmployee(Integer id) {
	    	 Employee emp = emprepo.findById(id)
				       .orElseThrow(()-> new EntityNotFoundException("Employee not found with id: " + id));

	    	 emprepo.delete(emp);
	    }
	    
	    
	    public List<EmployeeResponse> getEmployeesByDepartment(String department) {
	        List<Employee> employees = emprepo.findByDepartment(department);
	        if (employees.isEmpty()) {
	            throw new EntityNotFoundException("No employees found in department: " + department);
	        }
	        return employees.stream()
	        		.map(this::mapToResponse)
	        		.toList();
	    }

	    public List<EmployeeResponse> searchEmployeesByName(String keyword) {
	        List<Employee> employees = emprepo.findByNameContains(keyword);
	        if (employees.isEmpty()) {
	            throw new EntityNotFoundException("No employees found with name containing: " + keyword);
	        }
	        return employees.stream()
	        		.map(this::mapToResponse)
	        		.toList();
	    }

	    public List<EmployeeResponse> getEmployeesByDepartmentSorted(String department) {
	        List<Employee> employees = emprepo.findByDepartmentSortedByName(department);
	        if (employees.isEmpty()) {
	            throw new EntityNotFoundException("No employees found in department: " + department);
	        }
	        return employees.stream()
	        		.map(this::mapToResponse)
	        		.toList();
	    }
	    
	    

	 private EmployeeResponse mapToResponse(Employee emp) {
	        return EmployeeResponse.builder()
	                .id(emp.getId())
	                .name(emp.getName())
	                .department(emp.getDepartment())
	                .build();
	    }
	 
	 
	 
	 
}
