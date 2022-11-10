package com.cydeo.service.impl;

import com.cydeo.dto.TaskDTO;
import com.cydeo.entity.Task;
import com.cydeo.enums.Status;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.TaskRepository;
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

    public TaskServiceImpl(TaskRepository taskRepository, MapperUtil mapperUtil) {
        this.taskRepository = taskRepository;
        this.mapperUtil = mapperUtil;
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


}
