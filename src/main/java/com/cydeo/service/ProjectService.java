package com.cydeo.service;

import com.cydeo.dto.ProjectDTO;

import java.util.List;

public interface ProjectService {

    List<ProjectDTO> listAllProjects();

    void save(ProjectDTO projectDTO);

    void delete(String projectCode);

    void complete(String projectCode);

    ProjectDTO findByProjectCode(String projectCode);

    void update(ProjectDTO projectDTO);

    List<ProjectDTO> showProjectsStatusRelatedToAssignedManager();
}
