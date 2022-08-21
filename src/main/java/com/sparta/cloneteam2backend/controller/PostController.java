package com.sparta.cloneteam2backend.controller;

import com.sparta.cloneteam2backend.dto.ResponseDto;
import com.sparta.cloneteam2backend.dto.post.PostRequestDto;
import com.sparta.cloneteam2backend.model.Imgtarget;
import com.sparta.cloneteam2backend.model.Post;
import com.sparta.cloneteam2backend.service.PostService;
import com.sparta.cloneteam2backend.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/api/posts")
@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;
    private final S3Service s3Service;

    // 포스트 리스트 조회
    @GetMapping
    public ResponseEntity<ResponseDto> getPostList() {
        return new ResponseEntity<>(
                ResponseDto.success(postService.getPostList()), HttpStatus.OK);
    }

    // 포스트 상세 조회
    @GetMapping("/{postId}")
    public ResponseEntity<ResponseDto> getPostDetail(@PathVariable Long postId) {
        return new ResponseEntity<>(
                ResponseDto.success(postService.getPostDetail(postId)), HttpStatus.OK);
    }


    // 포스트 생성
    @PostMapping
    public ResponseEntity<ResponseDto> createPost(@RequestBody PostRequestDto requestDto,
                                                  @RequestPart(required = false) MultipartFile[] postImage) {
        Post post = postService.createPost(requestDto);
        if(postImage != null) {
            s3Service.uploadFile(postImage, String.valueOf(Imgtarget.POST), post.getPostId());
        }
        return new ResponseEntity<>(
                ResponseDto.success(post), HttpStatus.OK);
    }

    // 포스트 수정
    @PutMapping("/{postId}")
    public ResponseEntity<ResponseDto> updatePost(@PathVariable Long postId, @RequestBody PostRequestDto requestDto,
                                                  @RequestPart(required = false) MultipartFile[] postImage) {
        Post post = postService.createPost(requestDto);
        if(postImage != null) {
            s3Service.uploadFile(postImage, String.valueOf(Imgtarget.POST), postId);
        }
        return new ResponseEntity<>(
                ResponseDto.success(post), HttpStatus.OK);
    }

    // 포스트 삭제
    @DeleteMapping("/{postId}")
    public ResponseEntity<ResponseDto> deletePost(@PathVariable Long postId) {
        s3Service.deleteFile(Imgtarget.POST, postId);
        return new ResponseEntity<>(
                ResponseDto.success(postService.deletePost(postId)), HttpStatus.OK);
    }
}
