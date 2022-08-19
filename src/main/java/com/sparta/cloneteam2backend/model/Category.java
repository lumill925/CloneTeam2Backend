package com.sparta.cloneteam2backend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
@Getter
@AllArgsConstructor
public enum Category {
    pension("펜션"),
    apartment("아파트"),
    island("섬"),
    etc("기타");
    private String category;
}
