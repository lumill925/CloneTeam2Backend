package com.sparta.cloneteam2backend.controller;

import com.sparta.cloneteam2backend.dto.ResponseDto;
import com.sparta.cloneteam2backend.Dto.Auth.AuthRequestDto;
import com.sparta.cloneteam2backend.error.RestApiException;
import com.sparta.cloneteam2backend.service.AuthService;
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
    private final AuthService userService;


    @PostMapping("/signup")
    public ResponseEntity<ResponseDto> signup(@Valid @RequestBody AuthRequestDto requestDto) {
        try {
            return new ResponseEntity<>(ResponseDto.success(userService.signup(requestDto)), HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            RestApiException restApiException = new RestApiException();
            restApiException.setHttpStatus(HttpStatus.CONFLICT);
            restApiException.setErrorMessage(ex.getMessage());
            return new ResponseEntity(restApiException, HttpStatus.CONFLICT);
        }
    }


    @PostMapping("/login")
    public ResponseEntity<ResponseDto> login(@RequestBody AuthRequestDto requestDto) {
        return new ResponseEntity<>(ResponseDto.success(userService.login(requestDto)), HttpStatus.OK);
    }
}