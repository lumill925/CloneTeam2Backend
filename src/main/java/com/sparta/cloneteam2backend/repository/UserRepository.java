package com.sparta.cloneteam2backend.repository;

import com.sparta.cloneteam2backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUserUsername(String userUsername);
}
