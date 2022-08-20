package com.sparta.cloneteam2backend.Dto;

import com.sparta.cloneteam2backend.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private String userUsername;
    private String userNickname;
    private String userPassword;

    public UserResponseDto(String userUsername) {
    }

    public static UserResponseDto of(User user) {
        return new UserResponseDto(user.getUserUsername());
    }
}
