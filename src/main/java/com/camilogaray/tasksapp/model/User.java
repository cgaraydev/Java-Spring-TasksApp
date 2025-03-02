package com.camilogaray.tasksapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User {

    @Id
    @NotBlank(message = "El nombre de usuario no puede estar vacío")
    @Size(min = 3, max = 15, message = "El nombre de usuario debe tener entre 3 y 15 caracteres")
    private String username;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 3, message = "La contraseña debe tener al menos 3 caracteres")
    private String password;


    private Integer enabled = 1;

    public User() {
    }

    public User(String username, String password, Integer enabled) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer isEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", enabled=" + enabled +
                '}';
    }
}
