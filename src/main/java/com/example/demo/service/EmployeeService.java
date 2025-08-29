package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.EmployeeRequest;
import com.example.demo.dto.EmployeeResponse;

public interface EmployeeService {
	  EmployeeResponse createEmployee(EmployeeRequest request);
	  List<EmployeeResponse> GetAllEmployee();
	  EmployeeResponse getEmployeeById(Integer id);
	  EmployeeResponse updateEmployee(Integer id, EmployeeRequest request);
	  void deleteEmployee(Integer id);
//	  List<EmployeeResponse> getEmployeesByDepartment(String department);
//	  List<EmployeeResponse> searchEmployeesByName(String keyword);
//	  List<EmployeeResponse> getEmployeesByDepartmentSorted(String department);
}
