package com.hrant.survey_tool_spring_boot.service;

import com.hrant.survey_tool_spring_boot.dto.QuestionDTO;
import com.hrant.survey_tool_spring_boot.dto.QuestionResultDTO;
import com.hrant.survey_tool_spring_boot.dto.ResponseDTO;
import com.hrant.survey_tool_spring_boot.dto.SurveyDTO;
import com.hrant.survey_tool_spring_boot.entity.*;
import com.hrant.survey_tool_spring_boot.repository.QuestionRepository;
import com.hrant.survey_tool_spring_boot.repository.RatingRepository;
import com.hrant.survey_tool_spring_boot.repository.ResponseRepository;
import com.hrant.survey_tool_spring_boot.repository.SurveyRepository;
import jakarta.transaction.Transactional;
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

    public Optional<SurveyDTO> getSurveyById(Long id) {
        return surveyRepository.findByIdWithQuestions(id)
                .map(this::convertToSurveyDTO);
    }

    public List<SurveyDTO> getAllSurveys() {
        return surveyRepository.findAllWithQuestions().stream()
                .map(this::convertToSurveyDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void submitResponses(Long surveyId, List<ResponseDTO> responseDTOs) {
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new RuntimeException("Survey not found"));

        Response response = new Response();
        response.setSurvey(survey);

        Map<Long, Question> questionMap = loadQuestionsByIds(responseDTOs);
        Map<Long, Rating> ratingMap = loadRatingsByIds(responseDTOs);
        List<Answer> answers = buildAnswers(responseDTOs, questionMap, ratingMap, response);

        response.setAnswers(answers);
        responseRepository.save(response);
    }

    private Map<Long, Question> loadQuestionsByIds(List<ResponseDTO> responseDTOs) {
        Set<Long> ids = responseDTOs.stream()
                .map(ResponseDTO::getQuestionId)
                .collect(Collectors.toSet());

        return questionRepository.findAllById(ids).stream()
                .collect(Collectors.toMap(Question::getId, q -> q));
    }

    private Map<Long, Rating> loadRatingsByIds(List<ResponseDTO> responseDTOs) {
        Set<Long> ids = responseDTOs.stream()
                .map(ResponseDTO::getRatingId)
                .collect(Collectors.toSet());

        return ratingRepository.findAllById(ids).stream()
                .collect(Collectors.toMap(Rating::getId, r -> r));
    }

    private List<Answer> buildAnswers(List<ResponseDTO> dtos,
                                      Map<Long, Question> questionMap,
                                      Map<Long, Rating> ratingMap,
                                      Response response) {
        List<Answer> answers = new ArrayList<>();
        for (ResponseDTO dto : dtos) {
            Question question = questionMap.get(dto.getQuestionId());
            Rating rating = ratingMap.get(dto.getRatingId());

            if (question == null || rating == null) continue;

            Answer answer = new Answer();
            answer.setQuestion(question);
            answer.setRating(rating);
            answer.setResponse(response);

            answers.add(answer);
        }
        return answers;
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

    private SurveyDTO convertToSurveyDTO(Survey survey) {
        SurveyDTO dto = new SurveyDTO();
        dto.setId(survey.getId());
        dto.setTitle(survey.getTitle());
        dto.setQuestions(
                survey.getQuestions().stream()
                        .map(q -> new QuestionDTO(q.getId(), q.getText()))
                        .collect(Collectors.toList())
        );
        return dto;
    }
}
