package com.moon.todo.dto.todo;

import com.moon.todo.domain.Todo;
import com.moon.todo.domain.enums.EisenhowerType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class TodoResponse {
    private Long id;
    private String title;
    private String description;
    private LocalDate dueDate;
    private String tag;
    private boolean completed;
    private int pomodoroMinutes;
    private EisenhowerType priority;

    public static TodoResponse from(Todo todo) {
        return TodoResponse.builder()
                .id(todo.getId())
                .title(todo.getTitle())
                .description(todo.getDescription())
                .dueDate(todo.getDueDate())
                .tag(todo.getTag())
                .completed(todo.isCompleted())
                .pomodoroMinutes(todo.getPomodoroMinutes())
                .priority(todo.getPriority())
                .build();
    }
}
