package com.camilogaray.tasksapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/managers")
    public String managers() {
        return "managers";
    }

    @GetMapping("/admins")
    public String admins() {
        return "admins";
    }

    @GetMapping("/unauthorized")
    public String unauthorized() {
        return "unauthorized";
    }

    /*
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable int id) {
    Optional<Task> task = taskService.getTaskById(id);
    return task.map(ResponseEntity::ok)
               .orElseGet(() -> ResponseEntity.notFound().build());
    }
     */

}
