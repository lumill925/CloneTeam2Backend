package com.sparta.cloneteam2backend.dto.post;

import com.sparta.cloneteam2backend.model.Category;
import com.sparta.cloneteam2backend.model.Post;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class PostRequestDto {

    private String title;

    private String address;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String fee;

    public Post toCreate() {
        return Post.builder()
                .title(title)
                .address(address)
                .category(category)
                .fee(fee)
                .build();
    }

}
