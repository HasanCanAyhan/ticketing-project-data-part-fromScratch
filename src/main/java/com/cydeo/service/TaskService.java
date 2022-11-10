package com.cydeo.service;

import com.cydeo.dto.TaskDTO;

import java.util.List;

public interface TaskService {
    List<TaskDTO> listAllTasks();

    void save(TaskDTO taskDTO);

    void deleteById(Long id);

    TaskDTO findById(Long taskId);

    void update(TaskDTO taskDTO);
}
