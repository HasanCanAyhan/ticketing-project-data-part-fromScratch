package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.TaskDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.Project;
import com.cydeo.entity.User;
import com.cydeo.enums.Status;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.ProjectRepository;
import com.cydeo.service.ProjectService;
import com.cydeo.service.TaskService;
import com.cydeo.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final MapperUtil mapperUtil;


    private final UserService userService;
    private final TaskService taskService;


    public ProjectServiceImpl(ProjectRepository projectRepository, MapperUtil mapperUtil, UserService userService, @Lazy TaskService taskService) {
        this.projectRepository = projectRepository;
        this.mapperUtil = mapperUtil;
        this.userService = userService;
        this.taskService = taskService;
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

    @Override
    public List<ProjectDTO> showProjectsStatusRelatedToAssignedManager() {

        UserDTO userDTO_manager = userService.findByUserName("harold@manager.com");
        User user_manager = mapperUtil.convert(userDTO_manager, User.class);
        //find projects related to specific manager then, set those field
        // private int completeTaskCounts;
        // private int unfinishedTaskCounts;

        List<Project> projects = projectRepository.findAllByAssignedManager(user_manager);

        List<ProjectDTO> projectDTOList = projects.stream().map(project -> mapperUtil.convert(project, ProjectDTO.class)).collect(Collectors.toList());

        return projectDTOList.stream().map(projectDTO -> {

            projectDTO.setUnfinishedTaskCounts((int) taskService.findAllUnfinishedTaskRelatedToProject(projectDTO.getProjectCode()).stream().count());
            projectDTO.setCompleteTaskCounts((int) taskService.findAllFinishedTaskRelatedToProject(projectDTO.getProjectCode()).stream().count());
            return projectDTO;


        }).collect(Collectors.toList());


    }
}
