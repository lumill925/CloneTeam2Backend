package com.sparta.cloneteam2backend.controller;

import com.sparta.cloneteam2backend.dto.ResponseDto;
import com.sparta.cloneteam2backend.dto.post.PostRequestDto;
import com.sparta.cloneteam2backend.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

    // 포스트 리스트 조회
    @GetMapping("/api/posts")
    public ResponseEntity<ResponseDto> getPostList() {
        return new ResponseEntity<>(
                ResponseDto.success(postService.getPostList()), HttpStatus.OK);
    }

    // 포스트 생성
    @PostMapping("/api/posts")
    public ResponseEntity<ResponseDto> createPost(@RequestBody PostRequestDto requestDto) {

        return new ResponseEntity<>(
                ResponseDto.success(postService.createPost(requestDto)), HttpStatus.OK);
    }

    // 포스트 수정
    @PutMapping("/api/posts/{postId}")
    public ResponseEntity<ResponseDto> updatePost(@PathVariable Long postId, @RequestBody PostRequestDto requestDto) {
        return new ResponseEntity<>(
                ResponseDto.success(postService.updatePost(postId, requestDto)), HttpStatus.OK);
    }
}
