package com.camilogaray.tasksapp.config;

import com.camilogaray.tasksapp.exceptions.tasks.TaskListException;
import com.camilogaray.tasksapp.exceptions.tasks.TaskNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TaskNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleTaskNotFoundException(TaskNotFoundException e, Model model) {
        model.addAttribute("error", e.getMessage());
        return "error";
    }

    @ExceptionHandler(TaskListException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleTaskListException(TaskListException e, Model model) {
        model.addAttribute("error", e.getMessage());
        return "error";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleGeneralException(Exception e, Model model) {
        model.addAttribute("error", "Ocurrió un error inesperado. Inténtalo de nuevo.");
        return "error";
    }

    //    @ExceptionHandler(InvalidTaskException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public String handleInvalidTaskException(InvalidTaskException e, Model model) {
//        model.addAttribute("error", e.getMessage());
//        return "error";
//    }


}
