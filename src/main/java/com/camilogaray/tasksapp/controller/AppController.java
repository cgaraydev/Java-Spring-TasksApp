package com.camilogaray.tasksapp.controller;

import com.camilogaray.tasksapp.model.Task;
import com.camilogaray.tasksapp.service.ITaskService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

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
            return "redirect:/index";  // Redirige a "/index" si el usuario está autenticado
        } else {
            return "redirect:/login";  // Redirige a "/login" si el usuario no está autenticado
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
            return "redirect:/index";  // Redirige a "/index" si el usuario ya está autenticado
        }
        return "login";  // Muestra la página de login si el usuario no está autenticado
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
    /*
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable int id) {
    Optional<Task> task = taskService.getTaskById(id);
    return task.map(ResponseEntity::ok)
               .orElseGet(() -> ResponseEntity.notFound().build());
    }

      @GetMapping("/")
    public String home(HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/index";
        } else {
            return "redirect:/login";
        }
    }
     */