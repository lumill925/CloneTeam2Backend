package com.sparta.cloneteam2backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    @Column(nullable = false)
    private String userUsername;

    @Column(nullable = false)
    private String userNickname;

    @Column(nullable = false)
    @JsonIgnore
    private String userPassword;

    @Enumerated(EnumType.STRING)
    @JsonIgnore
    private Authority authority;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    @JsonIgnore
    private List<Review> reviews = new ArrayList<>();

    @Builder
    public User (String username, String nickname, String password, Authority authority) {
        this.userUsername = username;
        this.userNickname = nickname;
        this.userPassword = password;
        this.authority = authority;
    }
}