package com.moon.todo.controller;


import com.moon.todo.domain.User;
import com.moon.todo.domain.enums.EisenhowerType;
import com.moon.todo.dto.todo.TodoRequest;
import com.moon.todo.dto.todo.TodoResponse;
import com.moon.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;


    @PostMapping
    public ResponseEntity<TodoResponse> createTodo(
            @AuthenticationPrincipal User user,
            @RequestBody TodoRequest request
    ) {
        return ResponseEntity.ok(todoService.createTodo(user, request));
    }


    @GetMapping
    public ResponseEntity<List<TodoResponse>> getTodos(
            @AuthenticationPrincipal User user,
            @RequestParam(required = false, defaultValue = "false") Boolean completed
    ) {
        return ResponseEntity.ok(todoService.getTodos(user, completed));
    }


    @GetMapping("/{id}")
    public ResponseEntity<TodoResponse> getTodoById(
            @AuthenticationPrincipal User user,
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(todoService.getTodoById(user, id));
    }


    @PutMapping("/{id}")
    public ResponseEntity<TodoResponse> updateTodo(
            @AuthenticationPrincipal User user,
            @PathVariable Long id,
            @RequestBody TodoRequest request
    ) {
        return ResponseEntity.ok(todoService.updateTodo(user, id, request));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(
            @AuthenticationPrincipal User user,
            @PathVariable Long id
    ) {
        todoService.deleteTodo(user, id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<Void> toggleComplete(
            @AuthenticationPrincipal User user,
            @PathVariable Long id
    ) {
        todoService.toggleComplete(user, id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/pomodoro")
    public ResponseEntity<Void> addPomodoroTime(
            @AuthenticationPrincipal User user,
            @PathVariable Long id,
            @RequestParam("minutes") int minutes
    ) {
        todoService.addPomodoroTime(user, id, minutes);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/matrix")
    public List<TodoResponse> getTodosByMatrix(
            @AuthenticationPrincipal User user,
            @RequestParam EisenhowerType priority
    ) {
        return todoService.getTodosByPriority(user, priority);
    }

}

