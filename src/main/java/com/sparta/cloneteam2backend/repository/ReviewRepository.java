package com.sparta.cloneteam2backend.repository;

import com.sparta.cloneteam2backend.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
	List<Review> findAllByPostPostId(Long postId);
}