package com.sparta.cloneteam2backend.repository;

import com.sparta.cloneteam2backend.model.Facilities;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FacilitiesRepository extends JpaRepository<Facilities, Long> {

}
