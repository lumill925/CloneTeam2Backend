package com.sparta.cloneteam2backend.controller;

import com.sparta.cloneteam2backend.dto.ResponseDto;
import com.sparta.cloneteam2backend.dto.post.PostRequestDto;
import com.sparta.cloneteam2backend.dto.post.PostResponseDto;
import com.sparta.cloneteam2backend.model.*;
import com.sparta.cloneteam2backend.repository.ImgRepository;
import com.sparta.cloneteam2backend.service.FacilitiesService;
import com.sparta.cloneteam2backend.service.PostService;
import com.sparta.cloneteam2backend.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/api/posts")
@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;
    private final S3Service s3Service;
    private final FacilitiesService facilitiesService;

    // 포스트 리스트 조회
    @GetMapping
    public ResponseEntity<ResponseDto> getPostList(@RequestParam(required = false) Category postCategory, @RequestParam(required = false) String searchKeyword) {
        if(searchKeyword == null) {
            if(postCategory == null) {
                return new ResponseEntity<>(
                        ResponseDto.success(postService.getPostList()), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(
                        ResponseDto.success(postService.getPostCategoryList(postCategory)), HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(
                    ResponseDto.success(postService.getPostSearchList(searchKeyword)), HttpStatus.OK);
        }

    }

    // 포스트 상세 조회
    @GetMapping("/{postId}")
    public ResponseEntity<ResponseDto> getPostDetail(@PathVariable Long postId) {
        return new ResponseEntity<>(
                ResponseDto.success(postService.getPostDetail(postId)), HttpStatus.OK);
    }


    // 포스트 생성
    @PostMapping
    public ResponseEntity<ResponseDto> createPost(@RequestPart PostRequestDto requestDto,
                                                  @RequestPart(required = false) MultipartFile[] postImage) throws IllegalAccessException {
        PostResponseDto post = postService.createPost(requestDto);
        if(postImage != null) {
            s3Service.uploadFile(postImage, String.valueOf(Imgtarget.POST), post.getPostId());
        }
        if(requestDto.getFacilitiesList().size() != 0) {
            facilitiesService.createFacilities(post.getPostId(), requestDto.getFacilitiesList());
        }
        return new ResponseEntity<>(
                ResponseDto.success(post), HttpStatus.OK);
    }

    // 포스트 수정
    @PutMapping("/{postId}")
    public ResponseEntity<ResponseDto> updatePost(@PathVariable Long postId, @RequestPart PostRequestDto requestDto, ArrayList<String> deleteFiles,
            @RequestPart(required = false) MultipartFile[] postImage) throws IllegalAccessException {
        PostResponseDto post = postService.updatePost(postId, requestDto);
        if(postImage != null) {
            s3Service.updateFile(deleteFiles, postImage, String.valueOf(Imgtarget.POST), postId);
        }
        facilitiesService.updateFacilities(post.getPostId(), requestDto.getFacilitiesList());
        return new ResponseEntity<>(
                ResponseDto.success(post), HttpStatus.OK);
    }

    // 포스트 삭제
    @DeleteMapping("/{postId}")
    public ResponseEntity<ResponseDto> deletePost(@PathVariable Long postId) {
        s3Service.deleteFile(Imgtarget.POST, postId);
        facilitiesService.deleteFacilities(postId);
        return new ResponseEntity<>(
                ResponseDto.success(postService.deletePost(postId)), HttpStatus.OK);
    }
}
