package com.camilogaray.tasksapp.model;

import com.camilogaray.tasksapp.utils.TaskPriority;
import com.camilogaray.tasksapp.utils.TaskStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "El título no puede ser nulo")
    @Size(min = 1, max = 255, message = "El título debe tener entre 1 y 255 caracteres")
    private String title;

    @NotNull(message = "La descripción no puede ser nula")
    private String description;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "El estado no puede ser nulo")
    private TaskStatus status;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "La prioridad no puede ser nula")
    private TaskPriority priority;

    @ManyToOne
    @JoinColumn(name = "assigned_to", referencedColumnName = "username", nullable = false)
    @NotNull(message = "El usuario asignado no puede ser nulo")
    private User assignedTo;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @PrePersist
    protected void onCreate(){
        this.createdAt = LocalDateTime.now();
    }

    public Task() {
    }

    public Task(String title, String description, TaskStatus status, TaskPriority priority, User assignedTo) {
        this(title, description, status, priority, assignedTo, LocalDateTime.now(), null);
    }

    public Task(String title, String description, TaskStatus status, TaskPriority priority, User assignedTo,
                LocalDateTime createdAt, LocalDateTime completedAt) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.assignedTo = assignedTo;
        this.createdAt = (createdAt != null) ? createdAt : LocalDateTime.now();
        this.completedAt = completedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
        if (status == TaskStatus.COMPLETADO) {
            this.completedAt = LocalDateTime.now();
        } else {
            this.completedAt = null;
        }
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    public User getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(User assignedTo) {
        this.assignedTo = assignedTo;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        if (this.status == TaskStatus.COMPLETADO) {
            this.completedAt = completedAt;
        } else {
            this.completedAt = null;
        }
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", priority=" + priority +
                ", assignedTo=" + (assignedTo != null ? assignedTo.getUsername() : "null") +
                ", createdAt=" + createdAt +
                ", completedAt=" + completedAt +
                '}';
    }

}
