package com.hrant.survey_tool_spring_boot.dto;

public class ResponseDTO {
    private Long questionId;
    private Long ratingId;

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Long getRatingId() {
        return ratingId;
    }

    public void setRatingId(Long ratingId) {
        this.ratingId = ratingId;
    }
}

