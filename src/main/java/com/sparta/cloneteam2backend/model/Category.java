package com.sparta.cloneteam2backend.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
@Getter
@AllArgsConstructor
public enum Category {
    LOGCABIN("통나무집"),
    HANOK("한옥"),
    RYOKAN("료칸"),
    SHAREHOUSE("쉐어하우스"),
    CASTLE("캐슬");
    private String category;

    @JsonValue
    public String getCategory() {
        return category;
    }
}
