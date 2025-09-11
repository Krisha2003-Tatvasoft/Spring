package com.example.demo.service.impl;

import com.example.demo.entity.Employee;
import com.example.demo.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;

import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final EmployeeRepository employeeRepository;

    public CustomOAuth2UserService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println(">>> CustomOAuth2UserService.loadUser called");

        OAuth2User oauth2User = super.loadUser(userRequest);
        String email = oauth2User.getAttribute("email");

        if (email == null) {
            throw new OAuth2AuthenticationException("Email not found from OAuth2 provider");
        }

        Employee existingEmployee = employeeRepository.findByUsername(email);
        System.out.println("Existing employee: " + existingEmployee);

        if (existingEmployee == null) {
            Employee newEmployee = new Employee();
            newEmployee.setUsername(email);
            newEmployee.setName(oauth2User.getAttribute("name"));
            employeeRepository.saveAndFlush(newEmployee); // ensures DB insert
            System.out.println("Saved: " + newEmployee);
        }

        return oauth2User;
    }
}
