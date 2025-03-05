package com.camilogaray.tasksapp.controller;

import com.camilogaray.tasksapp.model.Task;
import com.camilogaray.tasksapp.model.User;
import com.camilogaray.tasksapp.service.ITaskService;
import com.camilogaray.tasksapp.service.IUserService;
import com.camilogaray.tasksapp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class AppController {

    private static final Logger logger = LoggerFactory.getLogger(AppController.class);
    private final ITaskService taskService;
    private final IUserService userService;
    private final List<String> usernames;

    public AppController(ITaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
        this.usernames = userService.getUsers().stream().map(User::getUsername).collect(Collectors.toList());
    }

    @GetMapping("/")
    public String root(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/index";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/index")
    public String index(Model model) {
        List<Task> tasks = taskService.getTasks();
        tasks.forEach(task -> logger.info(task.toString()));
        model.addAttribute("tasks", tasks);
        return "index";
    }

    @GetMapping("/login")
    public String login(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/index";
        }
        return "login";
    }

    @GetMapping("/task-details/{id}")
    public String viewTask(@PathVariable int id, Model model) {
        Task task = taskService.getTaskById(id);
        if (task != null) {
            model.addAttribute("task", task);
            return "task-details";
        } else {
            return "redirect:/index";
        }
    }

    @GetMapping("/edit-tasks")
    public String editTasks(Model model) {
        List<Task> tasks = taskService.getTasks();
        model.addAttribute("tasks", tasks);
        return "edit-tasks";
    }

    @GetMapping("/create-task")
    public String createTask(Model model) {
        model.addAttribute("taskForm", new Task());
        model.addAttribute("usernames", usernames);
        return "create-task";
    }

    @GetMapping("/create-task/{id}")
    public String showEditTask(@PathVariable(value = "id") Integer taskId, Model model) {
        Task task = taskService.getTaskById(taskId);
        logger.info("Tarea a editar: {}", task);
        model.addAttribute("taskForm", task);
        model.addAttribute("usernames", usernames);
        return "create-task";
    }

    @PostMapping("/create-task")
    public String editTask(@ModelAttribute("taskForm") Task task) {
        logger.info("Tarea a guardar: {}", task);
        taskService.saveTask(task);
        return "redirect:/edit-tasks";
    }

    @GetMapping("/delete-task/{id}")
    public String deleteTask(@PathVariable(value = "id") int taskId){
        logger.info("Eliminando tarea con ID: {}", taskId);
        taskService.deleteTask(taskId);
        return "redirect:/edit-tasks";
    }

    @GetMapping("/admins")
    public String admins() {
        return "admins";
    }

    @GetMapping("/unauthorized")
    public String unauthorized() {
        return "unauthorized";
    }

}