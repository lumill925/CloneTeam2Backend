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
public class PostMainResponseDto {
    private Long postId;

    private String postLocation;

    @Enumerated(EnumType.STRING)
    private Category postCategory;

    private String postFee;

    private Double reviewStar;

    private Object[] imageFiles;

    @Builder
    public PostMainResponseDto(Post post, Double reviewStar, List<Img> imageFiles) {
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
