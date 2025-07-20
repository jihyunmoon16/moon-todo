package com.moon.todo.service;

import com.moon.todo.domain.Todo;
import com.moon.todo.domain.User;
import com.moon.todo.domain.enums.EisenhowerType;
import com.moon.todo.dto.todo.TodoRequest;
import com.moon.todo.dto.todo.TodoResponse;
import com.moon.todo.repository.TodoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;


@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoResponse createTodo(User user, TodoRequest request) {
        Todo todo = Todo.builder()
                .user(user)
                .title(request.getTitle())
                .description(request.getDescription())
                .dueDate(request.getDueDate())
                .priority(request.getPriority())
                .tag(request.getTag())
                .completed(false)
                .pomodoroMinutes(0)
                .build();
        todoRepository.save(todo);
        return TodoResponse.from(todo);
    }

    public List<TodoResponse> getTodos(User user, LocalDate date) {
        List<Todo> todos = (date == null)
                ? todoRepository.findAllByUser(user)
                : todoRepository.findAllByUserAndDueDate(user, date);

        todos.sort(Comparator.comparingInt(todo -> todo.getPriority().getOrder()));

        return todos.stream().map(TodoResponse::from).collect(toList());
    }

    public TodoResponse getTodoById(User user, Long todoId) {
        Todo todo = findUserTodoOrThrow(user, todoId);
        return TodoResponse.from(todo);
    }

    public TodoResponse updateTodo(User user, Long id, TodoRequest request) {
        Todo todo = findUserTodoOrThrow(user, id);

        todo.setTitle(request.getTitle());
        todo.setDescription(request.getDescription());
        todo.setDueDate(request.getDueDate());
        todo.setTag(request.getTag());

        return TodoResponse.from(todoRepository.save(todo));
    }

    public void deleteTodo(User user, Long id) {
        Todo todo = findUserTodoOrThrow(user, id);
        todoRepository.delete(todo);
    }

    public void toggleComplete(User user, Long id) {
        Todo todo = findUserTodoOrThrow(user, id);
        todo.setCompleted(!todo.isCompleted());
        todoRepository.save(todo);
    }

    public void addPomodoroTime(User user, Long id, int minutes) {
        Todo todo = findUserTodoOrThrow(user, id);
        todo.setPomodoroMinutes(todo.getPomodoroMinutes() + minutes);
        todoRepository.save(todo);
    }

    public List<TodoResponse> getTodosByPriority(User user, EisenhowerType priority) {
        return todoRepository.findAllByUserAndPriority(user, priority)
                .stream().map(TodoResponse::from).collect(Collectors.toList());
    }

    // 공통 유틸: 사용자 소유 확인
    private Todo findUserTodoOrThrow(User user, Long todoId) {
        return todoRepository.findById(todoId)
                .filter(t -> t.getUser().getId().equals(user.getId()))
                .orElseThrow(() -> new EntityNotFoundException("해당 할 일을 찾을 수 없습니다."));
    }
}
