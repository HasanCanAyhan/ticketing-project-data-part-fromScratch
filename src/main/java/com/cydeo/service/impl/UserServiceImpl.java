package com.cydeo.service.impl;

import com.cydeo.dto.UserDTO;
import com.cydeo.entity.User;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.UserService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final MapperUtil mapperUtil;

    public UserServiceImpl(UserRepository userRepository, MapperUtil mapperUtil) {
        this.userRepository = userRepository;
        this.mapperUtil = mapperUtil;
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
        foundUser.setIsDeleted(true);
        userRepository.save(foundUser);

    }
}
