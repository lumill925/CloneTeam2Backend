package com.sparta.cloneteam2backend.service;

import com.sparta.cloneteam2backend.Dto.SignupRequestDto;
import com.sparta.cloneteam2backend.model.User;
import com.sparta.cloneteam2backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void registerUser(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String nickname = requestDto.getNickname();
        String password = requestDto.getPassword();
        //String passwordCheck = requestDto.getPasswordCheck();

//        if (userRepository.existsByUsername(requestDto.getUsername())) {
//            throw new InvalidValueException(ErrorCode.USERNAME_DUPLICATION);
//        }
//
//        if (username.length() < 4) {
//            throw new InvalidValueException(ErrorCode.INVALID_INPUT_USERNAME);
//        } else if (!Pattern.matches(pattern, username)) {
//            throw new InvalidValueException(ErrorCode.INVALID_USERNAME);
//        } else if (!password.equals(repassword)) {
//            throw new InvalidValueException(ErrorCode.NOTEQUAL_INPUT_PASSWORD);
//        } else if (password.length() < 4) {
//            throw new InvalidValueException(ErrorCode.INVALID_PASSWORD);
//        } else if (intro.length() < 1) {
//            throw new InvalidValueException(ErrorCode.INVALID_INPUT_INTRO);
//        }

        userRepository.save(new User(username, nickname, passwordEncoder.encode(password)));
    }
}
