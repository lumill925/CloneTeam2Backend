//package com.sparta.cloneteam2backend.security;
//
//import com.sparta.cloneteam2backend.model.User;
//import com.sparta.cloneteam2backend.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import javax.transaction.Transactional;
//
//@Service
//@RequiredArgsConstructor
//public class UserDetailService implements UserDetailsService {
//    private final UserRepository userRepository;
//
//    @Override
//    @Transactional
//    public UserDetail loadUserByUsername(String username) throws UsernameNotFoundException {
//        MapReactiveUserDetailsService userRepository;
//        User findUser = userRepository.findByUser_username(username)
//                .orElseThrow(() -> new UsernameNotFoundException("Can't find " + username));
//
//        if(findUser != null) {
//            return UserDetailService.builder()
//                    .username(findUser.getUsername())
//                    .password(findUser.getPassword())
//                    .build();
//        }
//        return null;
//    }
//}
