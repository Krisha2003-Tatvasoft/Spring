package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.service.MyUserDetailsService;
import com.example.demo.service.impl.CustomOAuth2UserService;

import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;


import lombok.RequiredArgsConstructor;



@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
//	by adding this dependency .. spring boot automatically …secure all end points..
//	spring security works on basic http
//	so it will automatically secure all end point and give us default user credential for user 1 in console..
//	we can use that for Authenticate all end point
//	but we want custom end point security and give more user
//	@EnableWebSecurity means : this annotation signals Spring to enable it’s security …it makes web secure..and use this configrations for security
//	websecurityconfigrerAdapter  : it is a utility class which provides a default configration and allowed customization of certain feature by extending it.
//	configure method which we have to override : this method provide how request are secured…it defiens how request should  match…what security action should we aplpied.(which one to secure and how to secure..)
	
	private final MyUserDetailsService myUserDetailsService;
	
	private final  CustomOAuth2UserService customOAuth2UserService;
	
	private final JwtFilter JwtFilter;
	
	 @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//	           http.csrf(customizer -> customizer.disable());
//	           http.authorizeRequests(request -> request.anyRequest().authenticated());
//	           http.httpBasic(Customizer.withDefaults());
//	           http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//		  return http.build();
//	           we can write like this 
//		 by default it goes for authenticate use UserPassword Authentication filter
//		 now for jwt based authenticaion we have make first filter is JWTFIlter and tell them like before UserPasswordFilter use JwtFilter and tell UsernamePasswordFilter directly continue if JWT authenticated 
	         
      		  return http.csrf(customizer -> customizer.disable())
       		   .authorizeHttpRequests(request -> 
        		             request
        		             .requestMatchers("login").permitAll()
    		             .anyRequest().hasAnyRole("USER" , "ADMIN"))
	        		   .httpBasic(Customizer.withDefaults())	        		  
	        		   .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	        		   .addFilterBefore(JwtFilter, UsernamePasswordAuthenticationFilter.class)
     		   .build();
		 
//		 return http.csrf(customizer -> customizer.disable())
//			        .authorizeHttpRequests(request -> request
//			            .requestMatchers("/login", "/oauth2/**", "/favicon.ico", "/accounts/**").permitAll()
//			            .anyRequest().authenticated())
//			        .oauth2Login(oauth2 -> oauth2
//			            .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService))) // use field!
//			        .build();
	    }
	 
	 @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
	 
	 @Bean
	 public AuthenticationProvider authenticationProvider() {
		 DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		 provider.setPasswordEncoder(passwordEncoder());
		 provider.setUserDetailsService(myUserDetailsService);
		 return provider;
	 }
	 
//	 for jwt token add we need to change in config in authentiactionManager and it is interface create an passing ac config u can cretae object of this 
	    @Bean
	    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
	        return config.getAuthenticationManager();
	    }
	 
	 

	 
}
