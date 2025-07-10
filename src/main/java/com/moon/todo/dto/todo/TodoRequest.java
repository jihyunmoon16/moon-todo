package com.moon.todo.dto.todo;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TodoRequest {
    private String title;
    private String description;
    private LocalDate dueDate;
    private String tag;
}
