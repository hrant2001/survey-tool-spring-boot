package com.hrant.survey_tool_spring_boot.repository;

import com.hrant.survey_tool_spring_boot.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
}
