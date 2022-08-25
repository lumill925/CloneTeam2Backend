package com.sparta.cloneteam2backend.controller;

import com.sparta.cloneteam2backend.dto.user.TokenDto;
import com.sparta.cloneteam2backend.dto.ResponseDto;
import com.sparta.cloneteam2backend.dto.user.UserRequestDto;
import com.sparta.cloneteam2backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;


    @PostMapping("/signup")
    public ResponseEntity<ResponseDto> signup(@Valid @RequestBody UserRequestDto requestDto) {
            return new ResponseEntity<>(ResponseDto.success(userService.signup(requestDto)), HttpStatus.OK);
    }


    @PostMapping("/login")
    public ResponseEntity<ResponseDto> login(@RequestBody UserRequestDto requestDto) {
		TokenDto tokenDto = userService.login(requestDto);
		MultiValueMap<String, String> header = new LinkedMultiValueMap<>();
		header.add("Access-Token", tokenDto.getAccessToken());
		header.add("Refresh-Token", tokenDto.getRefreshToken());
		header.add("Authorization", "Bearer " + tokenDto.getAccessToken());
		return new ResponseEntity<>(header, HttpStatus.OK);
    }


    @PostMapping("/reissue")
    public ResponseEntity<ResponseDto> reissue(@RequestBody TokenDto requestDto) {
            return new ResponseEntity<>(ResponseDto.success(userService.reissue(requestDto)), HttpStatus.OK);
    }


    @GetMapping("/me")
    public ResponseEntity<ResponseDto> getMy() {
            return new ResponseEntity<>(ResponseDto.success(userService.getMyInfo()), HttpStatus.OK);
    }


    @GetMapping("{userUsername}")
    public ResponseEntity<ResponseDto> getUser(@PathVariable String userUsername) {
            return new ResponseEntity<>(ResponseDto.success(userService.getUserInfo(userUsername)), HttpStatus.OK);
    }
}