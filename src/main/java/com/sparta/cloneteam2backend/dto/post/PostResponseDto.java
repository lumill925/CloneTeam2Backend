package com.sparta.cloneteam2backend.dto.post;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.cloneteam2backend.model.Category;
import com.sparta.cloneteam2backend.model.Facilities;
import com.sparta.cloneteam2backend.model.Img;
import com.sparta.cloneteam2backend.model.Post;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PostResponseDto {
    private Long postId;

    private String postLocation;

    @Enumerated(EnumType.STRING)
    private Category postCategory;

    private String postFee;

    private Double reviewStar;

    private Object[] imageFiles;

    @Builder
    public PostResponseDto(Post post, Double reviewStar, List<Img> imageFiles) {
        this.postId = post.getPostId();
        this.postLocation = post.getPostLocation();
        this.postCategory = post.getPostCategory();
        this.postFee = post.getPostFee();
        this.reviewStar = reviewStar;
        this.imageFiles = imageFiles.stream()
                .map(Img::getImgUrl)
                .toArray();
    }
}
