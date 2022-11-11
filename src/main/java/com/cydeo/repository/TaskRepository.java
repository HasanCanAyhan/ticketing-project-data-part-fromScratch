package com.cydeo.repository;

import com.cydeo.entity.Project;
import com.cydeo.entity.Task;
import com.cydeo.entity.User;
import com.cydeo.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {

    List<Task> findAllByIsDeleted(Boolean deleted);


    @Query("select t from Task t where t.project.projectCode = ?1 and t.taskStatus = 'COMPLETE' ")
    List<Task> findAllUnfinishedTaskRelatedToProject(String projectCode);

   @Query("select t from Task t where t.project.projectCode = ?1 and t.taskStatus <> 'COMPLETE' ")
    List<Task> findAllFinishedTaskRelatedToProject(String projectCode);

    List<Task> findAllByAssignedEmployeeAndTaskStatusIsNot(User user, Status status);
    List<Task> findAllByAssignedEmployeeAndTaskStatusIs(User user, Status status);

    List<Task> findAllByProject(Project project);

    List<Task> findAllByTaskStatusIsNotAndAssignedEmployee(Status status, User assignedEmployee);
}
