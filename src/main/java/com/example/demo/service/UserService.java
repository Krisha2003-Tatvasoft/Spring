package com.example.demo.service;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Properties.AppProperties;
import org.slf4j.Logger;


// User @service annotation for tell spring to create bean of this and also for specific type service
// Service layer is Always for Business logic
@Service
public class UserService {
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private AppProperties appProperties;
	
	
// logger is interface of (slf4j == simple logging facade for java ) which provide a method  like .Trace(most detailed) , .Debug , info , warn , error , Fatal
// we can not create direct object of interface  ==> LoggerFactory is a factory class which provide object of this and using that object we can use all method of logger
// we can give profile Specific  , package Specific  ,     
	
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	
//	one method for Creating method
	public String UserName(String name)
	{
		  logger.debug("UserName method called with name={}", name);
		if(name == null)
		{
			name ="User";
			logger.warn("Name was null or blank, defaulting to 'User'");
		}
		 logger.warn("Name was null or blank, defaulting to 'User'");
		return "Hello " + name +" " + appProperties.getSuffix();
	}
	
	public String UserDepartment(String name)
	{
		if(name == null)
		{
			name = "User";
		}
			String departmentName = departmentService.DepartmentName(1);
			return name + " "+ departmentName;
	}

}
