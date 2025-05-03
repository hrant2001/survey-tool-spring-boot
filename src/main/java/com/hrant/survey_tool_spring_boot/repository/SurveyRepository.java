package com.hrant.survey_tool_spring_boot.repository;

import com.hrant.survey_tool_spring_boot.entity.Survey;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Long> {
    @EntityGraph(attributePaths = {
            "questions",
            "questions.answers"
    })
    @Query("SELECT s FROM Survey s WHERE s.id = :id")
    Optional<Survey> findByIdWithQuestionsAndAnswers(@Param("id") Long id);

    @EntityGraph(attributePaths = "questions")
    List<Survey> findAll();

    @EntityGraph(attributePaths = {
            "questions",
            "questions.answers",
            "questions.answers.rating",
            "questions.answers.response",
            "questions.answers.response.survey"
    })
    @Query("SELECT s FROM Survey s")
    List<Survey> findAllWithQuestionsAndAnswers();
}
