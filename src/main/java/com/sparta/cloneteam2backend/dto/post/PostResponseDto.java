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

    private String title;

    private String address;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String fee;

    @Builder
    public PostResponseDto(Post post) {
        this.modifiedAt = post.getModifiedAt();
        this.postId = post.getPostId();
        this.title = post.getTitle();
        this.address = post.getAddress();
        this.category = post.getCategory();
        this.fee = post.getFee();
    }
}