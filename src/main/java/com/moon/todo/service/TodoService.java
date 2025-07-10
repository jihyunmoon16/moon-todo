package com.moon.todo.service;

import com.moon.todo.domain.Todo;
import com.moon.todo.domain.User;
import com.moon.todo.dto.todo.TodoRequest;
import com.moon.todo.dto.todo.TodoResponse;
import com.moon.todo.repository.TodoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;


@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    // 1. 할 일 생성
    public TodoResponse createTodo(User user, TodoRequest request) {
        Todo todo = Todo.builder()
                .user(user)
                .title(request.getTitle())
                .description(request.getDescription())
                .dueDate(request.getDueDate())
                .tag(request.getTag())
                .completed(false)
                .pomodoroMinutes(0)
                .build();
        todoRepository.save(todo);
        return TodoResponse.from(todo);
    }

    // 2. 할 일 목록 조회 (해당 사용자, 선택적 날짜 필터)
    public List<TodoResponse> getTodos(User user, LocalDate date) {
        List<Todo> todos = (date == null)
                ? todoRepository.findAllByUser(user)
                : todoRepository.findAllByUserAndDueDate(user, date);

        return todos.stream().map(TodoResponse::from).collect(toList());
    }

    // 3. 할 일 단건 조회
    public TodoResponse getTodoById(User user, Long todoId) {
        Todo todo = findUserTodoOrThrow(user, todoId);
        return TodoResponse.from(todo);
    }

    // 4. 할 일 수정
    public TodoResponse updateTodo(User user, Long id, TodoRequest request) {
        Todo todo = findUserTodoOrThrow(user, id);

        todo.setTitle(request.getTitle());
        todo.setDescription(request.getDescription());
        todo.setDueDate(request.getDueDate());
        todo.setTag(request.getTag());

        return TodoResponse.from(todoRepository.save(todo));
    }

    // 5. 삭제
    public void deleteTodo(User user, Long id) {
        Todo todo = findUserTodoOrThrow(user, id);
        todoRepository.delete(todo);
    }

    // 6. 완료 상태 토글
    public void toggleComplete(User user, Long id) {
        Todo todo = findUserTodoOrThrow(user, id);
        todo.setCompleted(!todo.isCompleted());
        todoRepository.save(todo);
    }

    // 7. 뽀모도로 시간 누적 (예: 25분)
    public void addPomodoroTime(User user, Long id, int minutes) {
        Todo todo = findUserTodoOrThrow(user, id);
        todo.setPomodoroMinutes(todo.getPomodoroMinutes() + minutes);
        todoRepository.save(todo);
    }

    // 공통 유틸: 사용자 소유 확인
    private Todo findUserTodoOrThrow(User user, Long todoId) {
        return todoRepository.findById(todoId)
                .filter(t -> t.getUser().getId().equals(user.getId()))
                .orElseThrow(() -> new EntityNotFoundException("해당 할 일을 찾을 수 없습니다."));
    }
}
