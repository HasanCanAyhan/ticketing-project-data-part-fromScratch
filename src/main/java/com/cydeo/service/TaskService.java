package com.cydeo.service;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.TaskDTO;
import com.cydeo.dto.UserDTO;

import java.util.Arrays;
import java.util.List;

public interface TaskService {
    List<TaskDTO> listAllTasks();

    void save(TaskDTO taskDTO);

    void deleteById(Long id);

    TaskDTO findById(Long taskId);

    void update(TaskDTO taskDTO);


    List<TaskDTO> findAllUnfinishedTaskRelatedToProject(String projectCode);


    List<TaskDTO> findAllFinishedTaskRelatedToProject(String projectCode);
}
