package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
//	by adding this dependency .. spring boot automatically …secure all end points..
//	spring security works on basic http
//	so it will automatically secure all end point and give us default user credential for user 1 in console..
//	we can use that for Authenticate all end point
//	but we want custom end point security and give more user
//	@EnableWebSecurity means : this annotation signals Spring to enable it’s security …it makes web secure..and use this configrations for security
//	websecurityconfigrerAdapter  : it is a utility class which provides a default configration and allowed customization of certain feature by extending it.
//	configure method which we have to override : this method provide how request are secured…it defiens how request should  match…what security action should we aplpied.(which one to secure and how to secure..)
	 
	 @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	        http
	            .authorizeHttpRequests(authorize -> authorize
	                .requestMatchers("/public/**").permitAll() // ✅ new method
	                .anyRequest().authenticated()
	            )
	            .formLogin(withDefaults()); // optional: default login form

	        return http.build();
	    }
//	 for security 6 +
//	 @Bean → This method creates something Spring can use globally.
//	 SecurityFilterChain → Think of this as a series of gates that every web request must pass through.
//	 HttpSecurity http → A helper to set up those gates: who can enter, who can’t, what kind of login to use, etc.
//	 other same  :::: .formLogin() → Enables a login page for your app.
//	 withDefaults() → Spring provides a default login page for you (you don’t have to create one yet).
	 
//	 extend with this class websecurityconfigrerAdapter
//	 @Override
//	 protected void configure(HttpSecurity http) throws Exception {
//		    http
//		        .authorizeRequests()
//		          .antMatchers("/public/**").permitAll()  // ❌ removed in Spring Security 6
//		          .anyRequest().authenticated()
//		        .and()
//		        .formLogin();
//		}

//	 earlier we have this...and this means..like this 
//	 authorizeRequests () : this tells spring security to start authorizing the request
//	  .antMatchers("/public/**").permitAll()  : this spesify the http request matching to this  path should be permited (allowed) for all user..they authenticated or not..
//	 .anyRequest().authenticated() : this tell any request which not been match earier should authenticated..means user should provided creadentials
//	         .and() : this for comes to root and start coinfigration from the root.
//	   .formLogin(); : this provide a default login form if user is not authenticated and try to acess secure end point it will redirect to this page..like defult if u do not give custom login page
//	 all method and their meaning in configration in security…
//	 U can access /hello without any authentication . however if u try to access another end point u will redirect to login form.
//	 means here if we do not give custom login page to this it will active default login form acive.

//	 spring security provide default controller for handling login path it is responsible for redirect to login for default login form…
//	 also provide a logout functionality

//	 basic authentication design is stateless ==⇒ means at every request u have to pass username and password in header…so each request do not know about the other request..
	 
}
