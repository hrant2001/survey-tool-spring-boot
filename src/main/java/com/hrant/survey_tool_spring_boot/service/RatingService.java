package com.hrant.survey_tool_spring_boot.service;

import com.hrant.survey_tool_spring_boot.entity.Rating;
import com.hrant.survey_tool_spring_boot.repository.RatingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;

    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    public List<Rating> getAllRatings() {
        return ratingRepository.findAll();
    }
}
