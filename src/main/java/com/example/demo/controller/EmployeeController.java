package com.example.demo.controller;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.dto.EmployeeRequest;
import com.example.demo.dto.EmployeeResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.service.EmployeeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class EmployeeController {
	
	private final EmployeeService service;
	
	@PostMapping("/employees")
    public EmployeeResponse createEmployee(@RequestBody EmployeeRequest request) {
        return service.createEmployee(request);
    }
	
	@GetMapping
    public List<EmployeeResponse> getAllEmployee()
    {
		return service.GetAllEmployee();
    }
	
	@GetMapping("/favicon.ico")
	@ResponseBody
	public void favicon() {
	    // do nothing
	}

	
	@PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
		
        return service.verify(request);
    }
	
	@GetMapping("/{id}")
    public EmployeeResponse getEmployeeById(@PathVariable Integer id) {
        return service.getEmployeeById(id);
    }
	
	@PutMapping("/{id}")
    public EmployeeResponse updateEmployee(@PathVariable Integer id, @RequestBody EmployeeRequest request) {
        return service.updateEmployee(id, request);
    }

    @DeleteMapping("/{id}")
    public String deleteEmployee(@PathVariable Integer id) {
        service.deleteEmployee(id);
        return ("Employee deleted successfully");
    }
    
//    @GetMapping("/department/{dept}")
//    public List<EmployeeResponse> getEmployeesByDepartment(@PathVariable String dept) {
//        return service.getEmployeesByDepartment(dept);
//    }
//    
//    @GetMapping("/search")
//    public List<EmployeeResponse> searchEmployeesByName(@RequestParam String keyword) {
//        return service.searchEmployeesByName(keyword);
//    }
//    
//    @GetMapping("/department/{dept}/sorted")
//    public List<EmployeeResponse> getEmployeesByDepartmentSorted(@PathVariable String dept) {
//        return service.getEmployeesByDepartmentSorted(dept);
//    }

//	@EnableMethodSecurity and we have a like this
//    we can d like this for authorization
//     @PreAuthorize("hasRole('ADMIN') and hasAuthority('USER_WRITE')") custom for role and permission based access
}
