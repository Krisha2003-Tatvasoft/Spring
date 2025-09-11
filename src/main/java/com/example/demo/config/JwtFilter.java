package com.example.demo.config;

import java.io.IOException;

//import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.service.MyUserDetailsService;
import com.example.demo.service.impl.jwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
	
	private final jwtService jwtService;
	
//	private final ApplicationContext context;
	
	private final MyUserDetailsService myUserDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authHeader = request.getHeader("Authorization");
		String token = null;
		String username = null;
		if(authHeader != null && authHeader.startsWith("Bearer "))
		{
//			Bearer+space = 7 cut and remaning is our token
			token = authHeader.substring(7);
			username = jwtService.extractUserName(token);
		}
		
//		now if username is not null and this user is not authenticate then we have to authenticate coz if already authenticate we do not need to authenticate agin
		if(username !=null && SecurityContextHolder.getContext().getAuthentication() == null)
		{
//			so for authenticate we have to verity token first then if token valid then we have to create authentication object
//			for validate token we have to validate token and also see that username we passed is available in databse
//			UserDetails userdetails = context.getBean(MyUserDetailsService.class).loadUserByUsername(username);
//			this we can do directly like this also
			UserDetails userdetails = myUserDetailsService.loadUserByUsername(username);
			if(jwtService.validateToken(token , userdetails)) {
//				if token is valid create authentication object for next filter and in that we have to pass three thing..principle(User Info), credential(password and all but we already validated so no nedded)
//				, authorities(roles and opermission this user have)
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userdetails , null , userdetails.getAuthorities());
//				like authToken know about User but do not know about request bject so ..for authToken know about request object like details about the which ip request come , thaier session key and all
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//				now token is ready now we have to store in context and added in chain
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		} 
//		after all this now we have to pass request to next filtrer 
		filterChain.doFilter(request, response);
		
	}
//	create this filter we extend some class first is OncePerRequestFilter = every request we want this filter to be executed 
//	this filter excuted onces a request === per request this filter excuted onces

}
