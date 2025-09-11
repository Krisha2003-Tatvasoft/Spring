package com.example.demo.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name="employess")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {
   
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false)
	private String name;
	
	
//	this is an owner fo r relation with department we givce FK here for Department
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "department_id")
	private Department department;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
	    name = "employee_project",
	    joinColumns = @JoinColumn(name = "employee_id"),
	    inverseJoinColumns = @JoinColumn(name = "project_id")
	)
	@JsonManagedReference
	@Builder.Default
	private List<Project> projects = new java.util.ArrayList<>();
	
    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    // optional: role
    private String role; // e.g. ROLE_USER, ROLE_ADMIN
	    
	
}
