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

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = false)
    private String fee;

    @Column(nullable = false)
    private String facilities;


    @Builder
    public Post(String title, String address, String content, Category category, String fee) {
        this.title = title;
        this.address = address;
        this.content = content;
        this.category = category;
        this.fee = fee;
    }

    public void update(String title, String address, String content, Category category, String fee) {
        this.title = title;
        this.address = address;
        this.content = content;
        this.category = category;
        this.fee = fee;
    }

}
