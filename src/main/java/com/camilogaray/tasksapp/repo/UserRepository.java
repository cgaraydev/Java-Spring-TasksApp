package com.camilogaray.tasksapp.repo;

import com.camilogaray.tasksapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);
}
