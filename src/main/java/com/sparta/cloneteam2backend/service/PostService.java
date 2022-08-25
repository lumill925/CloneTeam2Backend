package com.sparta.cloneteam2backend.service;

import com.sparta.cloneteam2backend.dto.post.PostDetailResponseDto;
import com.sparta.cloneteam2backend.dto.post.PostRequestDto;
import com.sparta.cloneteam2backend.dto.post.PostMainResponseDto;
import com.sparta.cloneteam2backend.dto.post.PostResponseDto;
import com.sparta.cloneteam2backend.error.ErrorCode;
import com.sparta.cloneteam2backend.error.exception.InvalidValueException;
import com.sparta.cloneteam2backend.model.*;
import com.sparta.cloneteam2backend.repository.FacilitiesRepository;
import com.sparta.cloneteam2backend.repository.ImgRepository;
import com.sparta.cloneteam2backend.repository.PostRepository;
import com.sparta.cloneteam2backend.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final ReviewRepository reviewRepository;
    private final ImgRepository imgRepository;
    private final FacilitiesRepository facilitiesRepository;
    private final UserService userService;


    // 포스트 리스트 조회
    public List<PostMainResponseDto> getPostList() {
        List<Post> posts = postRepository.findAllByOrderByCreatedAtDesc();
        List<PostMainResponseDto> postList = new ArrayList<>();
        for (Post post : posts) {
            Long postId = post.getPostId();
            Double reviewStar = reviewRepository.existsAllReviewStar(postId).orElse(0.0d);
            List<Img> imageFiles = imgRepository.findAllByTargetId(Imgtarget.POST, postId);
            PostMainResponseDto postMainResponseDto = PostMainResponseDto.builder()
                    .post(post)
                    .reviewStar(reviewStar)
                    .imageFiles(imageFiles)
                    .build();
            postList.add(postMainResponseDto);
        }
        return postList;
    }

    // 카테고리별 조회
    public List<PostMainResponseDto> getPostCategoryList(Category postCategory) {
        List<Post> posts = postRepository.findAllByPostCategory(postCategory);
        List<PostMainResponseDto> postList = new ArrayList<>();
        for (Post post : posts) {
            Long postId = post.getPostId();
            Double reviewStar = reviewRepository.existsAllReviewStar(postId).orElse(0.0d);
            List<Img> imageFiles = imgRepository.findAllByTargetId(Imgtarget.POST, postId);
            PostMainResponseDto postMainResponseDto = PostMainResponseDto.builder()
                    .post(post)
                    .reviewStar(reviewStar)
                    .imageFiles(imageFiles)
                    .build();
            postList.add(postMainResponseDto);
        }
        return postList;
    }

    // 검색 조회
    public List<PostMainResponseDto> getPostSearchList(String searchKeyword) {
        List<Post> posts = postRepository.findByPostContentContaining(searchKeyword);
        List<PostMainResponseDto> postList = new ArrayList<>();
        for (Post post : posts) {
            Long postId = post.getPostId();
            Double reviewStar = reviewRepository.existsAllReviewStar(postId).orElse(0.0d);
            List<Img> imageFiles = imgRepository.findAllByTargetId(Imgtarget.POST, postId);
            PostMainResponseDto postMainResponseDto = PostMainResponseDto.builder()
                    .post(post)
                    .reviewStar(reviewStar)
                    .imageFiles(imageFiles)
                    .build();
            postList.add(postMainResponseDto);
        }
        return postList;
    }

    // 포스트 상세 조회
    public PostDetailResponseDto getPostDetail(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new InvalidValueException(ErrorCode.NOTFOUND_POST));
        Double reviewStar = reviewRepository.existsAllReviewStar(postId).orElse(0.0d);
        List<Img> imageFiles = imgRepository.findAllByTargetId(Imgtarget.POST, postId);
        Facilities facilitiesList = facilitiesRepository.findByPostId(postId)
                .orElseThrow(() -> new InvalidValueException(ErrorCode.INVALID_INPUT_FACILITY));
        return PostDetailResponseDto.builder()
                .post(post)
                .reviewStar(reviewStar)
                .imageFiles(imageFiles)
                .facilitiesList(facilitiesList)
                .build();
    }

    // 포스트 생성
    @Transactional
    public PostResponseDto createPost(PostRequestDto requestDto) {

        if (requestDto.getPostTitle() == null || requestDto.getPostTitle().equals("")) {
            throw new InvalidValueException(ErrorCode.INVALID_INPUT_TITLE);
        } else if (requestDto.getPostCategory() == null || requestDto.getPostCategory().equals("")) {
            throw new InvalidValueException(ErrorCode.INVALID_INPUT_CATEGORY);
        } else if (requestDto.getPostAddress() == null || requestDto.getPostAddress().equals("")) {
            throw new InvalidValueException(ErrorCode.INVALID_INPUT_ADDRESS);
        } else if (requestDto.getPostContent() == null || requestDto.getPostContent().equals("")) {
            throw new InvalidValueException(ErrorCode.INVALID_INPUT_CONTENT);
        } else if (requestDto.getPostFee() == null || requestDto.getPostFee().equals("")) {
            throw new InvalidValueException(ErrorCode.INVALID_INPUT_FEE);
        }

        User user = userService.getMyInfo();
        Post post = requestDto.createPost(user);

        postRepository.save(post);
        return new PostResponseDto(post);
    }

    // 포스트 수정
    @Transactional
    public PostResponseDto updatePost(Long postId, PostRequestDto requestDto) {

        if (requestDto.getPostTitle() == null || requestDto.getPostTitle().equals("")) {
            throw new InvalidValueException(ErrorCode.INVALID_INPUT_TITLE);
        } else if (requestDto.getPostCategory() == null) {
            throw new InvalidValueException(ErrorCode.INVALID_INPUT_CATEGORY);
        } else if (requestDto.getPostAddress() == null || requestDto.getPostAddress().equals("")) {
            throw new InvalidValueException(ErrorCode.INVALID_INPUT_ADDRESS);
        } else if (requestDto.getPostContent() == null || requestDto.getPostContent().equals("")) {
            throw new InvalidValueException(ErrorCode.INVALID_INPUT_CONTENT);
        } else if (requestDto.getPostFee() == null || requestDto.getPostFee().equals("")) {
            throw new InvalidValueException(ErrorCode.INVALID_INPUT_FEE);
        }

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new InvalidValueException(ErrorCode.NOTFOUND_POST));
        if(!(userService.getMyInfo().getUserId().equals(post.getUser().getUserId())
                || userService.getMyInfo().getRole().equals(Authority.ROLE_ADMIN))) {
            throw new InvalidValueException(ErrorCode.NOT_AUTHORIZED_USER);
        }

        post.update(requestDto);
        return new PostResponseDto(post);
    }

    // 포스트 삭제
    @Transactional
    public Long deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new InvalidValueException(ErrorCode.NOTFOUND_POST));
        if(!(userService.getMyInfo().getUserId().equals(post.getUser().getUserId())
                || userService.getMyInfo().getRole().equals(Authority.ROLE_ADMIN))) {
            throw new InvalidValueException(ErrorCode.NOT_AUTHORIZED_USER);
        }
        postRepository.deleteById(postId);
        return postId;
    }

    // 포스트 객체 가져오기
    public Post getPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new InvalidValueException(ErrorCode.NOTFOUND_POST));
    }
}
