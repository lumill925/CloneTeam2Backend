package com.sparta.cloneteam2backend.service;

import com.sparta.cloneteam2backend.dto.post.PostRequestDto;
import com.sparta.cloneteam2backend.dto.post.PostResponseDto;
import com.sparta.cloneteam2backend.model.Post;
import com.sparta.cloneteam2backend.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public List<PostResponseDto> getPostList() {
        List<Post> posts = postRepository.findAllByOrderByCreatedAtDesc();
        List<PostResponseDto> postList = new ArrayList<>();
        for (Post post : posts) {
            PostResponseDto postResponseDto = PostResponseDto.builder()
                    .post(post)
                    .build();
            postList.add(postResponseDto);
        }
        return postList;
    }

    @Transactional
    public Post createPost(PostRequestDto requestDto) {
        Post post = requestDto.createPost();
        postRepository.save(post);
        return post;
    }

    @Transactional
    public Post updatePost(Long postId, PostRequestDto requestDto) {
        Post post = postRepository.findById(postId).orElseThrow();
        post.update(requestDto);
        return post;
    }
}
