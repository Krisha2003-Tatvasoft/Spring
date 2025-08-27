package com.example.demo.service;

import org.springframework.stereotype.Service;

@Service
public class DepartmentService {
	
	public String DepartmentName(int id)
	{
		String department;
		if(id ==0)
		{
			department= "General";
		}
		return "Department" + id;
	}

}
