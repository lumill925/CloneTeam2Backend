package com.sparta.cloneteam2backend.model;

import com.sparta.cloneteam2backend.dto.post.PostRequestDto;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Post extends Timestamped {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long postId;

    @Column(nullable = false)
    private String postTitle;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;

    @Column
    private String postLocation;

    @Column
    private String postAddress;

    @Column
    private String postContent;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category postCategory;

    @Column
    private String postFee;
    
    @Builder
    public Post(String postTitle, User user, String postLocation, String postAddress, String postContent, Category postCategory, String postFee) {
        this.postTitle = postTitle;
        this.user = user;
        this.postLocation = postLocation;
        this.postAddress = postAddress;
        this.postContent = postContent;
        this.postCategory = postCategory;
        this.postFee = postFee;
    }

    public void update(PostRequestDto requestDto) {
        this.postTitle = requestDto.getPostTitle();
        this.postLocation = requestDto.getPostLocation();
        this.postAddress = requestDto.getPostAddress();
        this.postContent = requestDto.getPostContent();
        this.postCategory = requestDto.getPostCategory();
        this.postFee = requestDto.getPostFee();
    }

}
