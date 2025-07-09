package com.moon.todo.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "pomodoro_sessions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PomodoroSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 사용자를 식별
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // 해당 집중 세션이 속한 할 일 (nullable 가능)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "todo_id")
    private Todo todo;

    // 집중 시작 시간
    @Column(name = "started_at", nullable = false)
    private LocalDateTime startedAt;

    // 집중 종료 시간
    @Column(name = "ended_at", nullable = false)
    private LocalDateTime endedAt;

    // 실제 집중에 성공한 시간 (분 단위, 예: 25분)
    @Column(name = "focus_minutes", nullable = false)
    private int focusMinutes;
}
