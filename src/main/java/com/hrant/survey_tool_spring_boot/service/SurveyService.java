package com.hrant.survey_tool_spring_boot.service;

import com.hrant.survey_tool_spring_boot.dto.QuestionResultDTO;
import com.hrant.survey_tool_spring_boot.dto.ResponseDTO;
import com.hrant.survey_tool_spring_boot.entity.*;
import com.hrant.survey_tool_spring_boot.repository.QuestionRepository;
import com.hrant.survey_tool_spring_boot.repository.RatingRepository;
import com.hrant.survey_tool_spring_boot.repository.ResponseRepository;
import com.hrant.survey_tool_spring_boot.repository.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SurveyService {
    private final SurveyRepository surveyRepository;
    private final QuestionRepository questionRepository;
    private final RatingRepository ratingRepository;
    private final ResponseRepository responseRepository;

    @Autowired
    public SurveyService(SurveyRepository surveyRepository,
                         QuestionRepository questionRepository,
                         RatingRepository ratingRepository,
                         ResponseRepository responseRepository) {
        this.surveyRepository = surveyRepository;
        this.questionRepository = questionRepository;
        this.ratingRepository = ratingRepository;
        this.responseRepository = responseRepository;
    }

    public Optional<Survey> getSurveyById(Long id) {
        return surveyRepository.findByIdWithQuestions(id);
    }

    public List<Survey> getAllSurveys() {
        return surveyRepository.findAllWithQuestions();
    }

    public void submitResponses(Long surveyId, List<ResponseDTO> responseDTOs) {
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new RuntimeException("Survey not found"));

        Response response = new Response();
        response.setSurvey(survey);

        Set<Long> questionIds = responseDTOs.stream()
                .map(ResponseDTO::getQuestionId)
                .collect(Collectors.toSet());

        Set<Long> ratingIds = responseDTOs.stream()
                .map(ResponseDTO::getRatingId)
                .collect(Collectors.toSet());

        Map<Long, Question> questionMap = questionRepository.findAllById(questionIds).stream()
                .collect(Collectors.toMap(Question::getId, q -> q));

        Map<Long, Rating> ratingMap = ratingRepository.findAllById(ratingIds).stream()
                .collect(Collectors.toMap(Rating::getId, r -> r));

        List<Answer> answers = new ArrayList<>();

        for (ResponseDTO dto : responseDTOs) {
            Question question = questionMap.get(dto.getQuestionId());
            Rating rating = ratingMap.get(dto.getRatingId());

            if (question == null || rating == null) continue;

            Answer answer = new Answer();
            answer.setQuestion(question);
            answer.setRating(rating);
            answer.setResponse(response);

            answers.add(answer);
        }

        response.setAnswers(answers);
        responseRepository.save(response);
    }

    public List<QuestionResultDTO> getSurveyResults(Long surveyId) {
        Survey survey = surveyRepository.findByIdWithQuestions(surveyId)
                .orElseThrow(() -> new RuntimeException("Survey not found"));

        List<QuestionResultDTO> resultDTOs = new ArrayList<>();

        for (Question question : survey.getQuestions()) {
            QuestionResultDTO dto = new QuestionResultDTO(question.getText());

            for (Answer answer : question.getAnswers()) {
                String label = answer.getRating().getLabel();
                dto.getRatingCounts().put(label,
                        dto.getRatingCounts().getOrDefault(label, 0L) + 1);
            }

            resultDTOs.add(dto);
        }

        return resultDTOs;
    }

}
