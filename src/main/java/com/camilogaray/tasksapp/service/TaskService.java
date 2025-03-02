package com.camilogaray.tasksapp.service;

import com.camilogaray.tasksapp.model.Task;
import com.camilogaray.tasksapp.repo.TaskRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService implements ITaskService {

    private final TaskRepository repo;

    public TaskService(TaskRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Task> getTasks() {
        return repo.findAll();
    }

    @Override
    public Optional<Task> getTaskById(int id) {
        return repo.findById(id);
    }

    @Override
    @Transactional
    public void saveTask(@Valid Task task) {
        repo.save(task);
    }

    @Override
    @Transactional
    public void deleteTask(int id) {
        repo.deleteById(id);
    }
}


/*
import static org.mockito.Mockito.*;
        import static org.junit.jupiter.api.Assertions.*;

        import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository repo;

    @InjectMocks
    private TaskService taskService;

    @Test
    public void testGetTaskById() {
        Task task = new Task();
        task.setId(1);
        when(repo.findById(1)).thenReturn(Optional.of(task));

        Optional<Task> result = taskService.getTaskById(1);
        assertTrue(result.isPresent());
        assertEquals(1, result.get().getId());
    }

    @Test
    public void testGetTaskByIdNotFound() {
        when(repo.findById(1)).thenReturn(Optional.empty());

        Optional<Task> result = taskService.getTaskById(1);
        assertFalse(result.isPresent());
    }
}

*/

/*
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ITaskService {
    public Page<Task> getTasks(Pageable pageable);
    public Optional<Task> getTaskById(int id);
    public void saveTask(Task task);
    public void deleteTask(int id);
}
Modificar el servicio:

java
Copy
@Override
public Page<Task> getTasks(Pageable pageable) {
    return repo.findAll(pageable);
}
Uso en el controlador:

java
Copy
@GetMapping
public ResponseEntity<Page<Task>> getTasks(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "id") String sortBy) {
    Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
    Page<Task> tasks = taskService.getTasks(pageable);
    return ResponseEntity.ok(tasks);
}
 */