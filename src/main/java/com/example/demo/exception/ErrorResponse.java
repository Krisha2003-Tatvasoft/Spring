package com.example.demo.exception;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {

//	    private int status;
//	    private String error;
//	    private String message;
//	    private LocalDateTime timestamp;
//	    private String path;
//
//	    public ErrorResponse(int status, String error, String message, LocalDateTime timestamp, String path) {
//	        this.status = status;
//	        this.error = error;
//	        this.message = message;
//	        this.timestamp = timestamp;
//	        this.path = path;
//	    }
//
//	    // Getters
//	    
//	    public int getStatus() { return status; }
//	    public String getError() { return error; }
//	    public String getMessage() { return message; }
//	    public LocalDateTime getTimestamp() { return timestamp; }
//	    public String getPath() { return path; } 
	
	
	    private int status;
	    private String error;
	    private String message;
	    private LocalDateTime timestamp;
	    private String path;
	    
}
