package com.example.demo.dto;

import java.util.List;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectRequest {
	  private String name;
	    private List<Integer> employeeIds;
}
