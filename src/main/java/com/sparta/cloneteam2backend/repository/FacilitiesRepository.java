package com.sparta.cloneteam2backend.repository;

import com.sparta.cloneteam2backend.model.Facilities;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FacilitiesRepository extends JpaRepository<Facilities, Long> {

    List<Facilities> deleteByPostId(Long postId);
}
