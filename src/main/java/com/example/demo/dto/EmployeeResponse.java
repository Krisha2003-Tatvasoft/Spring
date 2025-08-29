package com.example.demo.dto;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeResponse {
	    private Integer id;
	    private String name;
	    private String department;
}
