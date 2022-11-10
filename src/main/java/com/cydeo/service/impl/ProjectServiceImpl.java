package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.entity.Project;
import com.cydeo.enums.Status;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.ProjectRepository;
import com.cydeo.service.ProjectService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final MapperUtil mapperUtil;

    public ProjectServiceImpl(ProjectRepository projectRepository, MapperUtil mapperUtil) {
        this.projectRepository = projectRepository;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public List<ProjectDTO> listAllProjects() {
        List<Project> projects = projectRepository.findAllByIsDeletedOrderByProjectCode(false);

       return projects.stream()
                .map(project -> mapperUtil.convert(project,ProjectDTO.class))
                .collect(Collectors.toList());

    }

    @Override
    public void save(ProjectDTO projectDTO) {

        Project project = mapperUtil.convert(projectDTO, Project.class);

        project.setProjectStatus(Status.OPEN);
        projectRepository.save(project);
    }

    @Override
    public void delete(String projectCode) {

        Project project = projectRepository.findByProjectCode(projectCode);

        project.setIsDeleted(true);

        projectRepository.save(project);

    }

    @Override
    public void complete(String projectCode) {

        Project project = projectRepository.findByProjectCode(projectCode);

        project.setProjectStatus(Status.COMPLETE);

        projectRepository.save(project);

    }

    @Override
    public ProjectDTO findByProjectCode(String projectCode) {
        Project project = projectRepository.findByProjectCode(projectCode);
        return mapperUtil.convert(project,ProjectDTO.class);
    }

    @Override
    public void update(ProjectDTO projectDTO) {

        Project project = projectRepository.findByProjectCode(projectDTO.getProjectCode());

        Project convertPrj = mapperUtil.convert(projectDTO, Project.class);

        convertPrj.setId(project.getId());

        convertPrj.setProjectStatus(project.getProjectStatus());

        projectRepository.save(convertPrj);

    }
}
