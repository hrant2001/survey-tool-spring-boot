package com.hrant.survey_tool_spring_boot.dto;

import java.util.HashMap;
import java.util.Map;

public class QuestionResultDTO {
    private String questionText;
    private Map<String, Long> ratingCounts;

    public QuestionResultDTO(String questionText) {
        this.questionText = questionText;
        this.ratingCounts = new HashMap<>();
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public Map<String, Long> getRatingCounts() {
        return ratingCounts;
    }

    public void setRatingCounts(Map<String, Long> ratingCounts) {
        this.ratingCounts = ratingCounts;
    }
}
