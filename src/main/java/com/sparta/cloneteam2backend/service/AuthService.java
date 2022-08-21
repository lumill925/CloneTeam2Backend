package com.sparta.cloneteam2backend.service;

import com.sparta.cloneteam2backend.Dto.Auth.TokenDto;
import com.sparta.cloneteam2backend.Dto.Auth.AuthRequestDto;
import com.sparta.cloneteam2backend.Dto.Auth.AuthResponseDto;
import com.sparta.cloneteam2backend.jwt.TokenProvider;
import com.sparta.cloneteam2backend.model.RefreshToken;
import com.sparta.cloneteam2backend.model.User;
import com.sparta.cloneteam2backend.repository.RefreshTokenRepository;
import com.sparta.cloneteam2backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public AuthResponseDto signup(AuthRequestDto requestDto) {
        if (userRepository.existsByUserUsername(requestDto.getUserUsername())) {
            throw new IllegalArgumentException("이미 가입되어 있는 유저입니다");
        }

        User user = requestDto.toUser(passwordEncoder);
        return AuthResponseDto.of(userRepository.save(user));
    }


    public TokenDto login(AuthRequestDto requestDto) {
        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = requestDto.toAuthentication();

        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenDto tokenDto = tokenProvider.createToken(authentication);

        // 4. RefreshToken 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);

        // 5. 토큰 발급
        return tokenDto;
    }
}