package com.sparta.cloneteam2backend.service;

import com.sparta.cloneteam2backend.error.ErrorCode;
import com.sparta.cloneteam2backend.error.exception.InvalidValueException;
import com.sparta.cloneteam2backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;


@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUserUsername(username)
                .map(this::createUserDetails)
                .orElseThrow(() -> new InvalidValueException(ErrorCode.NOTFOUND_USER));
    }


    // DB 에 User 값이 존재한다면 UserDetails 객체로 만들어서 리턴
    private UserDetails createUserDetails(com.sparta.cloneteam2backend.model.User user) {
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(user.getAuthority().toString());

        return new User(
                String.valueOf(user.getUserUsername()),
                user.getUserPassword(),
                Collections.singleton(grantedAuthority)
        );
    }
}