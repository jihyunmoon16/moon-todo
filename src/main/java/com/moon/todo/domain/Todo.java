package com.moon.todo.domain;

import com.moon.todo.domain.enums.EisenhowerType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "todos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 연관된 사용자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // 할 일 제목
    @Column(nullable = false, length = 255)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EisenhowerType priority;

    // 선택사항: 할 일 설명
    @Column(columnDefinition = "TEXT")
    private String description;

    // 완료 여부
    @Column(nullable = false)
    private boolean completed;

    // 예정일 (하루/주간 캘린더 보기용)
    @Column(name = "due_date")
    private LocalDate dueDate;

    // 카테고리 or 태그
    @Column(length = 100)
    private String tag;

    // 뽀모도로 연동: 이 todo에 사용된 총 집중 시간 (분 단위)
    @Column(name = "pomodoro_minutes", nullable = false)
    private int pomodoroMinutes;

    // 생성일
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // 수정일
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
