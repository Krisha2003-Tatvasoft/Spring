package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee ,Integer > {

	 // Find employees by department using method name convention
//    List<Employee> findByDepartment(String department);
//
//    // Custom JPQL query - find by name containing keyword ===> Independed from the databse
//    
//    @Query("SELECT e FROM Employee e WHERE e.name LIKE %:keyword%")
//    List<Employee> findByNameContains(@Param("keyword") String keyword);
//
//    // Native SQL query example - employees in a department sorted by name ====> Spesific for the database...it is called native quary...
//    
//    @Query(value = "SELECT * FROM employess e WHERE e.department = :dept ORDER BY e.name", nativeQuery = true)
//    List<Employee> findByDepartmentSortedByName(@Param("dept") String department);
//    
//    pagination and sorting
//    projections
//    Modifying Query
//    give Spesification class also..
    
    
}
