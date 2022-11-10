package com.cydeo.repository;

import com.cydeo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {

    //get User based on username

    User findByUserName(String username);

    List<User> findAllByIsDeletedOrderByFirstName(Boolean deleted);

    List<User> findAllByRoleDescription(String description);

}
