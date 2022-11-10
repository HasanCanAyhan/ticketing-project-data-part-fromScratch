package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.TaskDTO;
import com.cydeo.entity.Project;
import com.cydeo.entity.Task;
import com.cydeo.enums.Status;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.TaskRepository;
import com.cydeo.service.ProjectService;
import com.cydeo.service.TaskService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final MapperUtil mapperUtil;

    private final ProjectService projectService;


    public TaskServiceImpl(TaskRepository taskRepository, MapperUtil mapperUtil, ProjectService projectService) {
        this.taskRepository = taskRepository;
        this.mapperUtil = mapperUtil;
        this.projectService = projectService;
    }

    @Override
    public List<TaskDTO> listAllTasks() {
        List<Task> tasks = taskRepository.findAllByIsDeleted(false);

        return tasks.stream()
                .map(task -> mapperUtil.convert(task,TaskDTO.class))
                .collect(Collectors.toList());

    }

    @Override
    public void save(TaskDTO taskDTO) {

        Task task = mapperUtil.convert(taskDTO, Task.class);
        task.setAssignedDate(LocalDate.now());
        task.setTaskStatus(Status.OPEN);
        taskRepository.save(task);

    }

    @Override
    public void deleteById(Long id) {

        Optional<Task> task = taskRepository.findById(id);

        if (task.isPresent()){
            task.get().setIsDeleted(true);
            taskRepository.save(task.get());
        }


    }

    @Override
    public TaskDTO findById(Long taskId) {

        Task task = taskRepository.findById(taskId).get();
        return mapperUtil.convert(task,TaskDTO.class);

    }

    @Override
    public void update(TaskDTO taskDTO) {

        Optional<Task> task = taskRepository.findById(taskDTO.getId());
        Task convertedTask = mapperUtil.convert(taskDTO, Task.class);

        if (task.isPresent()){

            convertedTask.setTaskStatus(task.get().getTaskStatus());
            convertedTask.setAssignedDate(task.get().getAssignedDate());
            taskRepository.save(convertedTask);
        }

    }



    @Override
    public List<TaskDTO> findAllUnfinishedTaskRelatedToProject(String projectCode) {

        ProjectDTO projectDTO = projectService.findByProjectCode(projectCode);
        Project project = mapperUtil.convert(projectDTO, Project.class);

        List<Task> taskList = taskRepository.findAllUnfinishedTaskRelatedToProject(project.getProjectCode());

        return taskList.stream()
                .map(task -> mapperUtil.convert(task,TaskDTO.class))
                .collect(Collectors.toList());


    }

    @Override
    public List<TaskDTO> findAllFinishedTaskRelatedToProject(String projectCode) {
        ProjectDTO projectDTO = projectService.findByProjectCode(projectCode);
        Project project = mapperUtil.convert(projectDTO, Project.class);

        List<Task> taskList =
                taskRepository.findAllFinishedTaskRelatedToProject(project.getProjectCode());

        return taskList.stream()
                .map(task -> mapperUtil.convert(task,TaskDTO.class))
                .collect(Collectors.toList());




    }


}
