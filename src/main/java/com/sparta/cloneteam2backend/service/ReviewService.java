package com.sparta.cloneteam2backend.service;

import com.sparta.cloneteam2backend.dto.review.ReviewRequestDto;
import com.sparta.cloneteam2backend.dto.review.ReviewResponseDto;
import com.sparta.cloneteam2backend.error.ErrorCode;
import com.sparta.cloneteam2backend.error.exception.InvalidValueException;
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

		if(!(requestDto.getReviewStar() <= 5 || requestDto.getReviewStar() >= 1)) {
			throw new InvalidValueException(ErrorCode.INVALID_INPUT_STAR);
		} else if (requestDto.getReviewContent() == null || requestDto.getReviewContent().equals("")) {
			throw new InvalidValueException(ErrorCode.INVALID_INPUT_COMMENT);
		}

		Post post = postService.getPost(postId);
		User user = userService.getMyInfo();
		Review review = requestDto.toReview(post, user);

		reviewRepository.save(review);
		return new ReviewResponseDto(review);
	}

	@Transactional
	public ReviewResponseDto updateReview(Long reviewId, ReviewRequestDto requestDto) {

		if(!(requestDto.getReviewStar() <= 5 || requestDto.getReviewStar() >= 1)) {
			throw new InvalidValueException(ErrorCode.INVALID_INPUT_STAR);
		} else if (requestDto.getReviewContent() == null || requestDto.getReviewContent().equals("")) {
			throw new InvalidValueException(ErrorCode.INVALID_INPUT_COMMENT);
		}

		Review review = reviewRepository.findById(reviewId)
				.orElseThrow(() -> new InvalidValueException(ErrorCode.NOTFOUND_COMMENT));
		if (!review.getUser().getUserId().equals(userService.getMyInfo().getUserId())) {
			throw new InvalidValueException(ErrorCode.NOT_AUTHORIZED_USER);
		}

		review.updateReview(requestDto);
		return new ReviewResponseDto(review);
	}

	@Transactional
	public void deleteReview(Long reviewId) {
		if (!reviewRepository.existsById(reviewId)) {
			throw new InvalidValueException(ErrorCode.NOTFOUND_COMMENT);
		}
		if (!reviewRepository.findById(reviewId).get().getUser().getUserId().equals(userService.getMyInfo().getUserId())) {
			throw new InvalidValueException(ErrorCode.NOT_AUTHORIZED_USER);
		}
		reviewRepository.deleteById(reviewId);
	}
}