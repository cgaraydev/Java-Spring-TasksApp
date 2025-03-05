package com.camilogaray.tasksapp.repo;

import com.camilogaray.tasksapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
