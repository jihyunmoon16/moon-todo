package com.moon.todo.domain.enums;

import lombok.Getter;
@Getter
public enum EisenhowerType {
    Q1("중요 & 긴급", 2),
    Q2("중요 & 비긴급", 1),
    Q3("비중요 & 긴급", 3),
    Q4("비중요 & 비긴급", 4);

    private final String label;
    private final int order;  // 🔥 우선순위 정렬용

    EisenhowerType(String label, int order) {
        this.label = label;
        this.order = order;
    }
}

