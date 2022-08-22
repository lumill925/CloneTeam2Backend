package com.sparta.cloneteam2backend.Dto.post;

import com.sparta.cloneteam2backend.model.Category;
import com.sparta.cloneteam2backend.model.Post;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
public class PostRequestDto {

    private String title;
    private String address;
    private String content;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String fee;

    public Post createPost() {
        return Post.builder()
                .title(title)
                .address(address)
                .content(content)
                .category(category)
                .fee(fee)
                .build();
    }

}
