package com.sparta.cloneteam2backend.dto.post;

import com.sparta.cloneteam2backend.model.Category;
import com.sparta.cloneteam2backend.model.Img;
import com.sparta.cloneteam2backend.model.Post;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

@Getter
public class PostResponseDto {
    private Long postId;

    private String postLocation;

    private String postContent;

    @Enumerated(EnumType.STRING)
    private Category postCategory;

    private String postFee;

    @Builder
    public PostResponseDto(Post post) {
        this.postId = post.getPostId();
        this.postLocation = post.getPostLocation();
        this.postCategory = post.getPostCategory();
        this.postContent = post.getPostContent();
        this.postFee = post.getPostFee();
    }
}
