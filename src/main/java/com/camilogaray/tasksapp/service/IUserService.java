package com.camilogaray.tasksapp.service;

import com.camilogaray.tasksapp.model.Task;
import com.camilogaray.tasksapp.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    public List<User> getUsers();
}
