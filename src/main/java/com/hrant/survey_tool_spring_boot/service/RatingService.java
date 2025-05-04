package com.hrant.survey_tool_spring_boot.service;

import com.hrant.survey_tool_spring_boot.dto.RatingDTO;
import com.hrant.survey_tool_spring_boot.repository.RatingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;

    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    public List<RatingDTO> getAllRatings() {
        return ratingRepository.findAll().stream()
                .map(rating -> new RatingDTO(rating.getId(), rating.getLabel()))
                .collect(Collectors.toList());
    }
}
