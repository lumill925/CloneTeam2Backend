package com.sparta.cloneteam2backend.controller;

import com.sparta.cloneteam2backend.Dto.SignupRequestDto;
import com.sparta.cloneteam2backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {
    private final UserService userService;

    @PostMapping("/signup")
    public String signup(@RequestBody SignupRequestDto requestDto) {
        System.out.println("여기");
        userService.registerUser(requestDto);
        //return ApiUtils.success(201, "회원가입에 성공하였습니다.");
        return requestDto.getUsername();
    }
}