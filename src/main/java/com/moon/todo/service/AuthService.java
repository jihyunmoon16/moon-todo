package com.moon.todo.service;

import com.moon.todo.config.JwtTokenProvider;
import com.moon.todo.domain.User;
import com.moon.todo.dto.auth.LoginRequest;
import com.moon.todo.dto.auth.LoginResponse;
import com.moon.todo.dto.auth.SignupRequest;
import com.moon.todo.exception.CustomException;
import com.moon.todo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public void signup(SignupRequest request) {
        // 이미 이메일이 존재하면 예외
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new CustomException("이미 등록된 이메일입니다.", HttpStatus.CONFLICT);
        }

        // 비밀번호 암호화
        String encryptedPassword = passwordEncoder.encode(request.getPassword());

        // 사용자 저장
        User newUser = User.builder()
                .email(request.getEmail())
                .password(encryptedPassword)
                .name(request.getName())
                .build();

        userRepository.save(newUser);

    }


    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new CustomException("존재하지 않는 이메일입니다.", HttpStatus.UNAUTHORIZED));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CustomException("비밀번호가 일치하지 않습니다.", HttpStatus.UNAUTHORIZED);
        }

        String token = jwtTokenProvider.createToken(user.getId(), user.getEmail());

        return new LoginResponse(user.getId(), user.getEmail(), user.getName(), token);
    }

}




