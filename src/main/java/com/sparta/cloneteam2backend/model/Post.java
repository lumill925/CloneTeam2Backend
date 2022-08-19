package com.sparta.cloneteam2backend.model;

import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Post extends Timestamped {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long postId;

    private String title;

    private String address;

    private String content;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String fee;

    private String facilities;


    @Builder
    public Post(String title, String address, String content, Category category, String fee) {
        this.title = title;
        this.address = address;
        this.content = content;
        this.category = category;
        this.fee = fee;
    }

}
