package com.camilogaray.tasksapp.controller;

import com.camilogaray.tasksapp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//@RestController
//@RequestMapping("/users")
//public class UserController {
//
//    @Autowired
//    private UserService userService;
//
//    @PostMapping
//    public User createUser(@RequestBody User user) {
//        return userService.createUser(user.getUsername(), user.getPassword(), user.isEnabled());
//    }
//
//    @PostMapping("/authenticate")
//    public boolean authenticateUser(@RequestParam String username, @RequestParam String password) {
//        return userService.authenticateUser(username, password);
//    }
//
//    @PutMapping("/{username}/password")
//    public void updatePassword(@PathVariable String username, @RequestParam String newPassword) {
//        User user = // Obtener el usuario de la base de datos
//                userService.updateUserPassword(user, newPassword);
//    }
//}