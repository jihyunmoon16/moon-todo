package com.moon.todo.repository;


import com.moon.todo.domain.Todo;
import com.moon.todo.domain.User;
import com.moon.todo.domain.enums.EisenhowerType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    // 사용자 기준 전체 할 일 목록
    List<Todo> findAllByUser(User user);

    // 사용자 + 날짜 기준 할 일 목록 (ex. 하루 단위 보기)
    List<Todo> findAllByUserAndDueDate(User user, LocalDate dueDate);

    // 사용자 + 완료 여부 기준 (필요시 사용 가능)
    List<Todo> findAllByUserAndCompleted(User user, boolean completed);

    // 사용자 + 우선순위 기준 할 일 목록
    List<Todo> findAllByUserAndPriority(User user, EisenhowerType priority);

    // 사용자 + 완료 여부 기준 할 일 목록
    List<Todo> findAllByUserAndCompleted(User user, Boolean completed);
}
