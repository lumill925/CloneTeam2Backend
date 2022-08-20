package com.sparta.cloneteam2backend.controller;

import com.sparta.cloneteam2backend.Dto.TokenDto;
import com.sparta.cloneteam2backend.Dto.UserRequestDto;
import com.sparta.cloneteam2backend.Dto.UserResponseDto;
import com.sparta.cloneteam2backend.error.RestApiException;
import com.sparta.cloneteam2backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signup(@Valid @RequestBody UserRequestDto requestDto) {
        try {
            return ResponseEntity.ok(userService.signup(requestDto));
        }
        catch (IllegalArgumentException ex) {
            RestApiException restApiException = new RestApiException();
            restApiException.setHttpStatus(HttpStatus.CONFLICT);
            restApiException.setErrorMessage(ex.getMessage());
            return new ResponseEntity(restApiException, HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody UserRequestDto requestDto) {
        return ResponseEntity.ok(userService.login(requestDto));
    }


    }