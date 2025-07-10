package com.moon.todo.controller;


import com.moon.todo.domain.User;
import com.moon.todo.dto.todo.TodoRequest;
import com.moon.todo.dto.todo.TodoResponse;
import com.moon.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    // 1. 할 일 생성
    @PostMapping
    public ResponseEntity<TodoResponse> createTodo(
            @AuthenticationPrincipal User user,
            @RequestBody TodoRequest request
    ) {
        return ResponseEntity.ok(todoService.createTodo(user, request));
    }

    // 2. 할 일 목록 조회 (날짜 필터 optional)
    @GetMapping
    public ResponseEntity<List<TodoResponse>> getTodos(
            @AuthenticationPrincipal User user,
            @RequestParam(value = "date", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        return ResponseEntity.ok(todoService.getTodos(user, date));
    }

    // 3. 할 일 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<TodoResponse> getTodoById(
            @AuthenticationPrincipal User user,
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(todoService.getTodoById(user, id));
    }

    // 4. 할 일 수정
    @PutMapping("/{id}")
    public ResponseEntity<TodoResponse> updateTodo(
            @AuthenticationPrincipal User user,
            @PathVariable Long id,
            @RequestBody TodoRequest request
    ) {
        return ResponseEntity.ok(todoService.updateTodo(user, id, request));
    }

    // 5. 할 일 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(
            @AuthenticationPrincipal User user,
            @PathVariable Long id
    ) {
        todoService.deleteTodo(user, id);
        return ResponseEntity.noContent().build();
    }

    // 6. 완료 여부 토글
    @PatchMapping("/{id}/complete")
    public ResponseEntity<Void> toggleComplete(
            @AuthenticationPrincipal User user,
            @PathVariable Long id
    ) {
        todoService.toggleComplete(user, id);
        return ResponseEntity.noContent().build();
    }

    // 7. 포모도로 시간 누적
    @PatchMapping("/{id}/pomodoro")
    public ResponseEntity<Void> addPomodoroTime(
            @AuthenticationPrincipal User user,
            @PathVariable Long id,
            @RequestParam("minutes") int minutes
    ) {
        todoService.addPomodoroTime(user, id, minutes);
        return ResponseEntity.noContent().build();
    }
}

