package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.TaskDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.User;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.ProjectService;
import com.cydeo.service.TaskService;
import com.cydeo.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final MapperUtil mapperUtil;


    private final ProjectService projectService;
    private final TaskService taskService;
    public UserServiceImpl(UserRepository userRepository, MapperUtil mapperUtil, ProjectService projectService, TaskService taskService) {
        this.userRepository = userRepository;
        this.mapperUtil = mapperUtil;
        this.projectService = projectService;
        this.taskService = taskService;
    }

    @Override
    public List<UserDTO> listAllUsers() {

        List<User> users = userRepository.findAllByIsDeletedOrderByFirstName(false);

        return users.stream().map(user -> mapperUtil.convert(user,UserDTO.class))
                .collect(Collectors.toList());

    }

    @Override
    public UserDTO findByUserName(String username) {

        User user = userRepository.findByUserName(username);

        return mapperUtil.convert(user,UserDTO.class);

    }

    @Override
    public void save(UserDTO userDTO) {

        User user = mapperUtil.convert(userDTO, User.class);
        userRepository.save(user);

    }

    @Override
    public void deleteByUserName(String username) {

    }

    @Override
    public void update(UserDTO userDTO) {

        User user = userRepository.findByUserName(userDTO.getUserName());//id

        User convertedUsr = mapperUtil.convert(userDTO, User.class);

        convertedUsr.setId(user.getId());

        userRepository.save(convertedUsr);


    }

    @Override
    public void delete(String username) { //soft delete

        User foundUser = userRepository.findByUserName(username);

        if (checkIfUserCanBeDeleted(foundUser)) {

            foundUser.setIsDeleted(true);
            //bug fixing to able to use same username  while creating user after deleting
            foundUser.setUserName(foundUser.getUserName() + "-" + foundUser.getId());
            userRepository.save(foundUser);

        }

    }

    @Override
    public List<UserDTO> listAllByRoleDescription(String description) {

        List<User> users = userRepository.findAllByRoleDescription(description);

        return users.stream()
                .map(user -> mapperUtil.convert(user,UserDTO.class))
                .collect(Collectors.toList());

    }


    private boolean checkIfUserCanBeDeleted(User user){

        switch (user.getRole().getDescription()){
            case "Manager":
                List<ProjectDTO> projectDTOList = projectService.listAllNonCompletedByAssignedManager(mapperUtil.convert(user,UserDTO.class));
                return projectDTOList.size() == 0;
            case "Employee":
                List<TaskDTO> taskDTOList = taskService.listAllNonCompletedByAssignedEmployee(mapperUtil.convert(user,UserDTO.class));
                return taskDTOList.size() == 0;
            default:
                return true;
        }

    }

}
