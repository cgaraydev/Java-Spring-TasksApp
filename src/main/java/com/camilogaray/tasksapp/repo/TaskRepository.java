package com.camilogaray.tasksapp.repo;

import com.camilogaray.tasksapp.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Integer> {
}
