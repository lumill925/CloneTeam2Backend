package com.sparta.cloneteam2backend.dto.post;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.cloneteam2backend.model.Category;
import com.sparta.cloneteam2backend.model.Post;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Getter
public class PostResponseDto {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime modifiedAt;

    private Long postId;

    private String postTitle;

    private String postAddress;

    @Enumerated(EnumType.STRING)
    private Category postCategory;

    private String postFee;

    @Builder
    public PostResponseDto(Post post) {
        this.modifiedAt = post.getModifiedAt();
        this.postId = post.getPostId();
        this.postTitle = post.getPostTitle();
        this.postAddress = post.getPostAddress();
        this.postCategory = post.getPostCategory();
        this.postFee = post.getPostFee();
    }
}
