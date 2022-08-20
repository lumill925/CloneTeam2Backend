package com.sparta.cloneteam2backend.service;

import com.sparta.cloneteam2backend.repository.FacilitiesRepository;
import com.sparta.cloneteam2backend.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FacilitiesService {

    private final PostRepository postRepository;

    private final FacilitiesRepository facilitiesRepository;


}
