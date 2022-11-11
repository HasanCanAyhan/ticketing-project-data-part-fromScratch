package com.cydeo.service;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.TaskDTO;
import com.cydeo.entity.Project;
import com.cydeo.enums.Status;

import java.util.List;

public interface TaskService {
    List<TaskDTO> listAllTasks();

    void save(TaskDTO taskDTO);

    void deleteById(Long id);

    TaskDTO findById(Long taskId);

    void update(TaskDTO taskDTO);


    List<TaskDTO> findAllUnfinishedTaskRelatedToProject(String projectCode);


    List<TaskDTO> findAllFinishedTaskRelatedToProject(String projectCode);

    List<TaskDTO> listAllTasksByStatusIsNot(Status status);

    List<TaskDTO> listAllTasksByStatus(Status status);

    void deleteByProject(ProjectDTO projectDTO);

    void completeByProject(ProjectDTO projectDTO);
}
