package com.hrant.survey_tool_spring_boot.service;

import com.hrant.survey_tool_spring_boot.dto.QuestionResultDTO;
import com.hrant.survey_tool_spring_boot.dto.ResponseDTO;
import com.hrant.survey_tool_spring_boot.entity.Answer;
import com.hrant.survey_tool_spring_boot.entity.Question;
import com.hrant.survey_tool_spring_boot.entity.Response;
import com.hrant.survey_tool_spring_boot.entity.Survey;
import com.hrant.survey_tool_spring_boot.repository.QuestionRepository;
import com.hrant.survey_tool_spring_boot.repository.RatingRepository;
import com.hrant.survey_tool_spring_boot.repository.ResponseRepository;
import com.hrant.survey_tool_spring_boot.repository.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        return surveyRepository.findById(id);
    }

    public void submitResponses(Long surveyId, List<ResponseDTO> responseDTOs) {
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new RuntimeException("Survey not found"));

        Response response = new Response();
        response.setSurvey(survey);

        List<Answer> answers = new ArrayList<>();

        for (ResponseDTO dto : responseDTOs) {
            var questionOpt = questionRepository.findById(dto.getQuestionId());
            var ratingOpt = ratingRepository.findById(dto.getRatingId());

            if (questionOpt.isEmpty() || ratingOpt.isEmpty()) continue;

            Answer answer = new Answer();
            answer.setQuestion(questionOpt.get());
            answer.setRating(ratingOpt.get());
            answer.setResponse(response);

            answers.add(answer);
        }

        response.setAnswers(answers);
        responseRepository.save(response);
    }

    public List<QuestionResultDTO> getSurveyResults(Long surveyId) {
        Survey survey = surveyRepository.findById(surveyId)
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
