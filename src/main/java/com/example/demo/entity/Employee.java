package com.example.demo.entity;

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
	
	private String department;
	
	
}
