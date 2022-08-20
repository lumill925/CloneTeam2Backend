package com.sparta.cloneteam2backend.service;

import com.sparta.cloneteam2backend.Dto.LoginRequestDto;
import com.sparta.cloneteam2backend.Dto.TokenDto;
import com.sparta.cloneteam2backend.jwt.TokenProvider;
import com.sparta.cloneteam2backend.model.User;
import com.sparta.cloneteam2backend.repository.RefreshTokenRepository;
import com.sparta.cloneteam2backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void registerUser(SignupRequestDto requestDto) {
        String username = requestDto.getUserUsername();
        String nickname = requestDto.getUserNickname();
        String password = requestDto.getUserPassword();
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

    public ResponseEntity<TokenDto> login(LoginRequestDto requestDto) {
        User user = userRepository.findByUserUsername(requestDto.getUserUsername());

        if (!passwordEncoder.matches(requestDto.getUserPassword(), user.getUserPassword())) {
            System.out.println("비밀번호가 틀렸따.");
            //throw new InvalidValueException(ErrorCode.LOGIN_INPUT_INVALID);
        }
        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = requestDto.toAuthentication();

        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

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
