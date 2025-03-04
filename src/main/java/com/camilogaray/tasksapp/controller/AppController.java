package com.camilogaray.tasksapp.controller;

import com.camilogaray.tasksapp.model.Task;
import com.camilogaray.tasksapp.service.ITaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Controller
public class AppController {

    private static final Logger logger = LoggerFactory.getLogger(AppController.class);
    private final ITaskService taskService;

    public AppController(ITaskService taskService) {
        this.taskService = taskService;
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
        Optional<Task> task = taskService.getTaskById(id);
        if (task.isPresent()) {
            model.addAttribute("task", task.get());
            return "task-details";
        } else {
            return "redirect:/index";
        }
    }

    @GetMapping("/edit-tasks")
    public String editTasks(Model model) {
        List<Task> tasks = taskService.getTasks(); // Obtener todas las tareas
        model.addAttribute("tasks", tasks); // Pasar las tareas a la vista
        return "edit-tasks";
    }

    @GetMapping("/admins")
    public String admins() {
        return "admins";
    }

    @GetMapping("/unauthorized")
    public String unauthorized() {
        return "unauthorized";
    }

    @GetMapping("/create-task")
    public String createTask(){
        return "create-task";
    }

}