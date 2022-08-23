package com.sparta.cloneteam2backend.service;

import com.sparta.cloneteam2backend.dto.review.ReviewRequestDto;
import com.sparta.cloneteam2backend.dto.review.ReviewResponseDto;
import com.sparta.cloneteam2backend.model.Post;
import com.sparta.cloneteam2backend.model.Review;
import com.sparta.cloneteam2backend.model.User;
import com.sparta.cloneteam2backend.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {
	private final ReviewRepository reviewRepository;
	private final PostService postService;
	private final UserService userService;

	@Transactional
	public List<ReviewResponseDto> getReview(Long postId) {
		List<Review> reviews = reviewRepository.findAllByPostPostId(postId);
		return reviews.stream()
				.map(ReviewResponseDto::new)
				.collect(Collectors.toList());
	}

	public ReviewResponseDto createReview(Long postId, ReviewRequestDto requestDto) {
		Post post = postService.getPost(postId);
		User user = userService.getMyInfo();
		Review review = requestDto.toReview(post, user);
		reviewRepository.save(review);
		return new ReviewResponseDto(review);
	}

	@Transactional
	public ReviewResponseDto updateReview(Long reviewId, ReviewRequestDto requestDto) {
		Review review = reviewRepository.findById(reviewId)
				.orElseThrow(() -> new IllegalArgumentException(""));
		if (!review.getUser().getUserId().equals(userService.getMyInfo().getUserId())) {
			throw new IllegalArgumentException("자신의 리뷰만 수정할 수 있습니다.");
		}
		review.updateReview(requestDto);
		return new ReviewResponseDto(review);
	}

	@Transactional
	public void deleteReview(Long reviewId) {
		if (!reviewRepository.findById(reviewId).get().getUser().getUserId().equals(userService.getMyInfo().getUserId())) {
			throw new IllegalArgumentException("자신의 리뷰만 삭제할 수 있습니다.");
		}
		reviewRepository.deleteById(reviewId);
	}
}