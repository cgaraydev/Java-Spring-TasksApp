package com.camilogaray.tasksapp.service;

import com.camilogaray.tasksapp.exceptions.users.UserNotFoundException;
import com.camilogaray.tasksapp.model.User;
import com.camilogaray.tasksapp.repo.TaskRepository;
import com.camilogaray.tasksapp.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

    private final UserRepository repo;

    public UserService(UserRepository repo){
        this.repo = repo;
    }

    @Override
    public List<User> getUsers() {
        return repo.findAll();
    }

    @Override
    public User getUserByName(String username) {
        return repo.findByUsername(username).orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));
    }
}



//@Autowired
//private PasswordEncoderService passwordEncoderService;
//
//public User createUser(String username, String rawPassword, boolean enabled) {
//    String encryptedPassword = passwordEncoderService.encryptPassword(rawPassword);
//    User user = new User(username, encryptedPassword, enabled);
//    // Guardar el usuario en la base de datos
//    return user;
//}
//
//public boolean authenticateUser(String username, String rawPassword) {
//    User user = // Obtener el usuario de la base de datos
//    return passwordEncoderService.checkPassword(rawPassword, user.getPassword());
//}
//
//public void updateUserPassword(User user, String newRawPassword) {
//    String encryptedPassword = passwordEncoderService.encryptPassword(newRawPassword);
//    user.setPassword(encryptedPassword);
//    // Actualizar el usuario en la base de datos
//}