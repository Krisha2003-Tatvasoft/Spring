package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Value;


import com.example.demo.service.UserService;

import lombok.RequiredArgsConstructor;


//RestController reurn json data and all not required to add @response body for return data ...in normal only for returning Data not view (Rest APi) 
//@Controller it always return a view like Html or jsp like that like for return views + data (web pages)

@RestController
@RequiredArgsConstructor
public class UserController {
    
//	@Autowird == we can do it using this also DI for create object and all but it handle in testing is tough 
//	@AutoWired needed when we have multiple constructor 
//	when u want to call at service in constructor u have to do this service inject using constructor coz otherwise it will happen after constructor call
	
//	like this 
//	  public GreetingController(GreetingService greetingService) {
//        this.greetingService = greetingService;
//    }
	
	
//	@Autowired
//	private final UserService userService;
//	
//	public UserController(UserService userService)
//	{
//		this.userService = userService;
//	}
	
//	we can do like this also
	
	
//	using @RequiredArgsConstructor so not need do di for all it will create automatically by spring
	
//	@Autowired
//	private  UserService userService;
	
	private final UserService userService;
	
	@Value("${app.preffix}")
    private String preffix;
	
	
	 @GetMapping("/User")
	    public String GetWelcomeMessage(@RequestParam(defaultValue = "User") String name) {
	        return preffix + " " + userService.UserName(name);
	    }
	
	 @GetMapping("/UserDepartment")
	    public String GetUserDepartment(@RequestParam(defaultValue = "User") String name) {
	        return userService.UserDepartment(name);
	    }
	
	
}
