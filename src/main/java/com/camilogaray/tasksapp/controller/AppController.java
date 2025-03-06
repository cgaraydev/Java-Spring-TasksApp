package com.camilogaray.tasksapp.controller;

import com.camilogaray.tasksapp.model.Task;
import com.camilogaray.tasksapp.model.User;
import com.camilogaray.tasksapp.service.ITaskService;
import com.camilogaray.tasksapp.service.IUserService;
import com.camilogaray.tasksapp.service.UserService;
import com.camilogaray.tasksapp.utils.TaskStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

//    @GetMapping("/index")
//    public String index(Model model) {
//        List<Task> tasks = taskService.getTasks();
//        tasks.forEach(task -> logger.info(task.toString()));
//        model.addAttribute("tasks", tasks);
//        return "index";
//    }

    @GetMapping("/index")
    public String index(Model model) {
        List<Task> allTasks = taskService.getTasks();

        // Filtrar tareas pendientes
        List<Task> pendingTasks = allTasks.stream()
                .filter(task -> task.getStatus() == TaskStatus.PENDIENTE)
                .collect(Collectors.toList());

        // Filtrar tareas completadas o canceladas
        List<Task> completedOrCanceledTasks = allTasks.stream()
                .filter(task -> task.getStatus() == TaskStatus.COMPLETADO || task.getStatus() == TaskStatus.CANCELADO)
                .collect(Collectors.toList());

        // Agregar las listas al modelo
        model.addAttribute("pendingTasks", pendingTasks);
        model.addAttribute("completedOrCanceledTasks", completedOrCanceledTasks);

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

    @GetMapping("/manage-tasks")
    public String manageTasks(Model model) {
        List<Task> tasks = taskService.getTasks();
        model.addAttribute("tasks", tasks);
        return "manage-tasks";
    }

    @GetMapping("/create-update-task")
    public String createUpdateTask(Model model) {
        model.addAttribute("taskForm", new Task());
        model.addAttribute("usernames", usernames);
        return "create-update-task";
    }

    @GetMapping("/create-update-task/{id}")
    public String showEditTask(@PathVariable(value = "id") Integer taskId, Model model) {
        Task task = taskService.getTaskById(taskId);
        logger.info("Tarea a editar: {}", task);
        model.addAttribute("taskForm", task);
        model.addAttribute("usernames", usernames);
        return "create-update-task";
    }

    @PostMapping("/create-update-task")
    public String editTask(@ModelAttribute("taskForm") Task task) {
        logger.info("Tarea a guardar: {}", task);
        taskService.saveTask(task);
        return "redirect:/manage-tasks";
    }

    @GetMapping("/delete-task/{id}")
    public String deleteTask(@PathVariable(value = "id") int taskId) {
        logger.info("Eliminando tarea con ID: {}", taskId);
        taskService.deleteTask(taskId);
        return "redirect:/manage-tasks";
    }

    @PostMapping("/complete-task/{id}")
    public String completeTask(@PathVariable(value = "id") int taskId) {
        Task task = taskService.getTaskById(taskId);
        if (task != null) {
            task.setStatus(TaskStatus.COMPLETADO);
            taskService.saveTask(task);
        }
        return "redirect:/index";
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