package com.example.demo.dto;

//import java.util.List;
//
//import com.example.demo.entity.Project;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequest {
	 private String username;
	    private String password;

}
