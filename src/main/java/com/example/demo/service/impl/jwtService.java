package com.example.demo.service.impl;



//import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class jwtService {
	
	 private final SecretKey  key;
	
//	it will create a random secret key for u
	 public jwtService() {
	        String secret = "helloIamworkingInDemoSpringBootProject";
	        this.key = Keys.hmacShaKeyFor(secret.getBytes()); // Convert string to bytes and create HMAC key
	    }
	
//	give a spesific key ==> 
	
	public String generateToken(String username)
	{
		
		Map<String , Object> claims = new HashMap<>();
		
		return Jwts.builder()
				.claims()
				.add(claims)
				.subject(username)  
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + 60 * 60 * 30))
				.and()
				.signWith(key)
				.compact();
                
	}
	
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = Jwts.parser()        
                .verifyWith(key)            
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claimsResolver.apply(claims);
    }

	    // Extract username from token
	    public String extractUserName(String token) {
	        return extractClaim(token, Claims::getSubject);
	    }

	    // Validate token
	    public boolean validateToken(String token, UserDetails userDetails) {
	        String username = extractUserName(token);
	        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	    }

	    // Check if token expired
	    private boolean isTokenExpired(String token) {
	        Date expiration = extractClaim(token, Claims::getExpiration);
	        return expiration.before(new Date());
	    }}
