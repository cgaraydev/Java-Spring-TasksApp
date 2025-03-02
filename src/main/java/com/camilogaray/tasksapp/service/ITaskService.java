package com.camilogaray.tasksapp.service;

import com.camilogaray.tasksapp.model.Task;

import java.util.List;
import java.util.Optional;

public interface ITaskService {
    public List<Task> getTasks();
    public Optional<Task> getTaskById(int id);
    public void saveTask(Task task);
    public void deleteTask(int id);
}
