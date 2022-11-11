package com.cydeo.repository;

import com.cydeo.entity.Project;
import com.cydeo.entity.User;
import com.cydeo.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project,Long> {

    List<Project> findAllByIsDeletedOrderByProjectCode(Boolean deleted);

    Project findByProjectCode(String projectCode);

    List<Project> findAllByAssignedManager(User user);


    List<Project> findAllByProjectStatusIsNotAndAssignedManager(Status complete, User assignedManager);
}
