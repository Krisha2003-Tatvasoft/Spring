package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
	 
	 
//	 for Exception 
	 @GetMapping("/UserId/{id}")
	 public String getUser(@PathVariable int id)
	 {
		 if(id <0 )
		 {
			 throw new IllegalArgumentException("ID must be positive");
		 }
		 if (id == 0) {
	            throw new NullPointerException("Simulated NPE");
	        }
	        return "user-" + id;
	 }
	 
//	 @ExceptionHandler(IllegalArgumentException.class)
//	    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex) {
//	        return ResponseEntity
//	                .status(HttpStatus.BAD_REQUEST) // return 400
//	                .body("Validation Error: " + ex.getMessage());
//	    }
//
//	    @ExceptionHandler(NullPointerException.class)
//	    public ResponseEntity<String> handleNpe(NullPointerException ex) {
//	        return ResponseEntity
//	                .status(HttpStatus.INTERNAL_SERVER_ERROR) // still 500
//	                .body("Unexpected error occurred: " + ex.getMessage());
//	    }
//	
//	    for Global level Exception Handle
	    
	    @GetMapping("/UserIDGlobalEx/{id}")
	    public String getUserGlobalEx(@PathVariable int id) {
	        if (id < 0) throw new IllegalArgumentException("ID must be positive");
	        if (id == 0) throw new NullPointerException("Simulated NPE");
	        return "user-" + id;
	    }
	    
//	   advanced we can Use ResponseEntityExceptionHandler =====> which is class ==> spring will handle some exception handle in global level 
//	    u can extend that and override their default method for create your custom Error response create
	    
//      Add thorws when any method may throw an exception 	    
	
	    
//	    For special case we can handle by try catch using we can do 
	    @GetMapping("/UserSafe/{id}")
	    public ResponseEntity<String> getUserSafe(@PathVariable int id) {
	        try {
	            if (id < 0) {
	                throw new IllegalArgumentException("ID must be positive");
	            }
	            if (id == 0) {
	                throw new NullPointerException("Simulated NPE");
	            }
	            return ResponseEntity.ok("user-" + id);

	        } catch (IllegalArgumentException ex) {
	            // handled only in this method
	            return ResponseEntity
	                    .status(HttpStatus.BAD_REQUEST)
	                    .body("Handled locally: " + ex.getMessage());

	        } catch (Exception ex) {
	            // fallback for any other exception
	            return ResponseEntity
	                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
	                    .body("Local error: " + ex.getMessage());
	        }
	    }

}
