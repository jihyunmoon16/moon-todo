package com.moon.todo.repository;

import com.moon.todo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // 이메일로 사용자 찾기 (로그인 시 사용)
    Optional<User> findByEmail(String email);

    // 이메일 존재 여부 확인 (회원가입 중복 체크 시 사용)
    boolean existsByEmail(String email);
}
