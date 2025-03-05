package com.camilogaray.tasksapp.controller;

import com.camilogaray.tasksapp.model.Task;
import com.camilogaray.tasksapp.model.User;
import com.camilogaray.tasksapp.service.ITaskService;
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

@Controller
public class AppController {

    private static final Logger logger = LoggerFactory.getLogger(AppController.class);
    private final ITaskService taskService;
    private final UserService userService;

    public AppController(ITaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
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
        List<Task> tasks = taskService.getTasks();
        model.addAttribute("tasks", tasks);
        return "edit-tasks";
    }

    @GetMapping("/create-task")
    public String createTask(Model model) {
        model.addAttribute("taskForm", new Task());
        model.addAttribute("users", userService.getUsers());
        return "create-task";
    }

//    @PostMapping("/create-task")
//    public String saveTask(@ModelAttribute("taskForm") Task task) {
//        taskService.saveTask(task);
//        return "redirect:/index";
//    }

//    @PostMapping("/create-task")
//    public String saveTask(@ModelAttribute("taskForm") Task task) {
//        if (task.getId() != null) { // Si tiene un ID, es una actualización
//            Optional<Task> existingTaskOptional = taskService.getTaskById(task.getId());
//            existingTaskOptional.ifPresent(existingTask -> {
//                existingTask.setTitle(task.getTitle());
//                existingTask.setDescription(task.getDescription());
//                existingTask.setStatus(task.getStatus());
//                existingTask.setPriority(task.getPriority());
//                existingTask.setAssignedTo(task.getAssignedTo());
//                taskService.saveTask(existingTask);
//            });
//        } else {
//            taskService.saveTask(task);
//        }
//        return "redirect:/index";
//    }

//    @PostMapping("/create-task")
//    public String saveTask(@ModelAttribute("taskForm") Task task) {
//        if (task.getId() != null) { // Si tiene un ID, es una actualización
//            Optional<Task> existingTaskOptional = taskService.getTaskById(task.getId());
//            if (existingTaskOptional.isPresent()) {
//                Task existingTask = existingTaskOptional.get();
//                existingTask.setTitle(task.getTitle());
//                existingTask.setDescription(task.getDescription());
//                existingTask.setStatus(task.getStatus());
//                existingTask.setPriority(task.getPriority());
//
//                // Obtener el usuario asignado desde el formulario
//                User assignedUser = userService.getUserByName(task.getAssignedTo().getUsername());
//                existingTask.setAssignedTo(assignedUser);
//
//                taskService.saveTask(existingTask);
//            } else {
//                return "redirect:/index"; // O lanzar una excepción personalizada
//            }
//        } else {
//            // Obtener el usuario asignado desde el formulario
//            User assignedUser = userService.getUserByName(task.getAssignedTo().getUsername());
//            task.setAssignedTo(assignedUser);
//
//            taskService.saveTask(task); // Crear una nueva tarea
//        }
//        return "redirect:/index";
//    }

//    @PostMapping("/create-task")
//    public String saveTask(@ModelAttribute("taskForm") Task task) {
//        if (task.getId() != null) { // Si tiene un ID, es una actualización
//            Optional<Task> existingTaskOptional = taskService.getTaskById(task.getId());
//            existingTaskOptional.ifPresent(existingTask -> {
//                existingTask.setTitle(task.getTitle());
//                existingTask.setDescription(task.getDescription());
//                existingTask.setStatus(task.getStatus());
//                existingTask.setPriority(task.getPriority());
//                existingTask.setAssignedTo(task.getAssignedTo());
//                taskService.saveTask(existingTask); // Guarda la tarea actualizada
//            });
//        } else {
//            taskService.saveTask(task); // Si no tiene ID, es una nueva tarea
//        }
//        return "redirect:/index"; // Redirige a la página de tareas después de guardar
//    }

    @GetMapping("/edit-task/{id}")
    public String editTask(@PathVariable Integer id, Model model) {
        Optional<Task> task = taskService.getTaskById(id);
        if (task.isEmpty()) return "redirect:/index";
        model.addAttribute("taskForm", task.get());
        model.addAttribute("users", userService.getUsers());
        return "create-task";
    }

    @PostMapping("/create-task")
    public String saveTask(@ModelAttribute("taskForm") Task task) {
        if (task.getId() != null) { // Si tiene un ID, es una actualización
            Optional<Task> existingTaskOptional = taskService.getTaskById(task.getId());
            if (existingTaskOptional.isPresent()) {
                Task existingTask = existingTaskOptional.get();
                existingTask.setTitle(task.getTitle());
                existingTask.setDescription(task.getDescription());
                existingTask.setStatus(task.getStatus());
                existingTask.setPriority(task.getPriority());

                // Buscamos el usuario por su username
                User assignedUser = userService.getUserByName(task.getAssignedTo().getUsername());
                existingTask.setAssignedTo(assignedUser);

                taskService.saveTask(existingTask);
            } else {
                return "redirect:/index"; // O lanzar una excepción personalizada
            }
        } else {
            taskService.saveTask(task); // Si no tiene ID, es una nueva tarea
        }
        return "redirect:/index";
    }
//
//    @GetMapping("/edit-task/{id}")
//    public String editTask(@PathVariable Integer id, Model model) {
//        Optional<Task> task = taskService.getTaskById(id);
//        if (task.isEmpty()) return "redirect:/index";
//        model.addAttribute("taskForm", task.get());
//        model.addAttribute("users", userService.getUsers());
//        return "create-task";
//    }
//
//    @PostMapping("/create-task")
//    public String saveTask(@ModelAttribute("taskForm") Task task) {
//        if (task.getId() != null) { // Si tiene un ID, es una actualización
//            Optional<Task> existingTaskOptional = taskService.getTaskById(task.getId());
//            if (existingTaskOptional.isPresent()) {
//                Task existingTask = existingTaskOptional.get();
//                existingTask.setTitle(task.getTitle());
//                existingTask.setDescription(task.getDescription());
//                existingTask.setStatus(task.getStatus());
//                existingTask.setPriority(task.getPriority());
//                existingTask.setAssignedTo(task.getAssignedTo());
//                taskService.saveTask(existingTask);
//            } else {
//                return "redirect:/index"; // O lanzar una excepción personalizada
//            }
//        } else {
//            taskService.saveTask(task); // Si no tiene ID, es una nueva tarea
//        }
//        return "redirect:/index";
//    }

//    @GetMapping("/edit-task/{id}")
//    public String editTask(@PathVariable Integer id, Model model) {
//        Optional<Task> task = taskService.getTaskById(id);
//        if (task.isEmpty()) {
//            return "redirect:/index"; // O lanzar una excepción personalizada
//        }
//        model.addAttribute("taskForm", task.get()); // Envía la tarea al formulario
//        model.addAttribute("users", userService.getUsers()); // Envía la lista de usuarios
//        return "create-task";
//    }

//    @GetMapping("/edit-task/{id}")
//    public String editTask(@PathVariable Integer id, Model model) {
//        Optional<Task> task = taskService.getTaskById(id);
//        if (task.isEmpty()) return "redirect:/index";
//        model.addAttribute("taskForm", task.get());
//        model.addAttribute("users", userService.getUsers());
//        return "create-task";
//    }

//    @PostMapping("/create-task")
//    public String addTask(@ModelAttribute("taskForm") Task task, @RequestParam String assignedTo) {
//        User user = userService.getUserById(assignedTo); // Obtener el usuario por su username
//        task.setAssignedTo(user); // Asignar el usuario a la tarea
//        logger.info("Tarea a agregar: " + task);
//        taskService.saveTask(task);
//        return "redirect:/edit-tasks";
//    }

//    @PostMapping("/create-task")
//    public String addTask(@ModelAttribute("taskForm") Task task) {
//        logger.info("Tarea a agregar: " + task);
//        taskService.saveTask(task);
//        return "redirect:/edit-tasks";
//    }

    @GetMapping("/admins")
    public String admins() {
        return "admins";
    }

    @GetMapping("/unauthorized")
    public String unauthorized() {
        return "unauthorized";
    }

}