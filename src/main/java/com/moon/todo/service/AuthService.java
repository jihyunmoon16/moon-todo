package com.moon.todo.service;

import com.moon.todo.domain.User;
import com.moon.todo.dto.auth.SignupRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

//    public void signup(SignupRequest request) {
//        // 이미 이메일이 존재하면 예외
//        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
//            throw new CustomException("이미 등록된 이메일입니다.", HttpStatus.CONFLICT);
//        }
//
//        // 비밀번호 암호화
//        String encryptedPassword = passwordEncoder.encode(request.getPassword());
//
//        // 사용자 저장
//        User newUser = User.builder()
//                .email(request.getEmail())
//                .password(encryptedPassword)
//                .name(request.getName())
//                .build();
//
//        userRepository.save(newUser);
//
//        return new RegisterResponse(newUser.getId(), newUser.getEmail(), newUser.getName());
//    }

}


