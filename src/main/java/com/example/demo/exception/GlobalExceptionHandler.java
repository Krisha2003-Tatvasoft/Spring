package com.example.demo.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
	
//	 // Handle IllegalArgumentException globally
//    @ExceptionHandler(IllegalArgumentException.class)
//    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex,
//                                                                HttpServletRequest request) {
//        ErrorResponse error = new ErrorResponse(
//                HttpStatus.BAD_REQUEST.value(),
//                "Bad Request",
//                ex.getMessage(),
//                LocalDateTime.now(),
//                request.getRequestURI()
//        );
//        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
//    }
//
//    // Handle generic Exception globally
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex,
//                                                             HttpServletRequest request) {
//        ErrorResponse error = new ErrorResponse(
//                HttpStatus.INTERNAL_SERVER_ERROR.value(),
//                "Internal Server Error",
//                ex.getMessage(),
//                LocalDateTime.now(),
//                request.getRequestURI()
//        );
//        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
	
	
	   @ExceptionHandler(IllegalArgumentException.class)
	    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex,
	                                                                HttpServletRequest request) {
	        log.warn("IllegalArgumentException: {}", ex.getMessage()); // logs automatically

	        ErrorResponse error = ErrorResponse.builder()
	                .status(HttpStatus.BAD_REQUEST.value())
	                .error("Bad Request")
	                .message(ex.getMessage())
	                .timestamp(LocalDateTime.now())
	                .path(request.getRequestURI())
	                .build();

	        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	    }

	   
	    @ExceptionHandler(Exception.class)
	    public ResponseEntity<ErrorResponse> handleAll(Exception ex,  HttpServletRequest request) {
	        log.error("Unexpected Exception", ex); 

	        ErrorResponse error = ErrorResponse.builder()
	                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
	                .error("Internal Server Error")
	                .message(ex.getMessage())
	                .timestamp(LocalDateTime.now())
	                .path(request.getRequestURI())
	                .build();

	        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	    
	    @ExceptionHandler(EntityNotFoundException.class)
	    public ResponseEntity<ErrorResponse> handleEntityNotFound(EntityNotFoundException ex,
	                                                              HttpServletRequest request) {
	        log.warn("EntityNotFoundException: {}", ex.getMessage());

	        ErrorResponse error = ErrorResponse.builder()
	                .status(HttpStatus.NOT_FOUND.value())
	                .error("Not Found")
	                .message(ex.getMessage())
	                .timestamp(LocalDateTime.now())
	                .path(request.getRequestURI())
	                .build();

	        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	    }



}
