package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.ProjectRequest;
import com.example.demo.entity.Project;

public interface ProjectService {
	Project createProject(ProjectRequest  project);
    List<Project> getAllProjects();
}
