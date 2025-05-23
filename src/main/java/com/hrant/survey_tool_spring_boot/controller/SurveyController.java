package com.hrant.survey_tool_spring_boot.controller;

import com.hrant.survey_tool_spring_boot.dto.QuestionResultDTO;
import com.hrant.survey_tool_spring_boot.dto.ResponseDTO;
import com.hrant.survey_tool_spring_boot.dto.SurveyDTO;
import com.hrant.survey_tool_spring_boot.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/surveys")
public class SurveyController {

    private final SurveyService surveyService;

    @Autowired
    public SurveyController(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<SurveyDTO> getSurveyById(@PathVariable Long id) {
        return surveyService.getSurveyById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("")
    public ResponseEntity<List<SurveyDTO>> getAllSurveys() {
        return ResponseEntity.ok(surveyService.getAllSurveys());
    }

    @PostMapping("/{id}/responses")
    public ResponseEntity<Void> submitResponse(@PathVariable Long id, @RequestBody List<ResponseDTO> responseDTOs) {
        surveyService.submitResponses(id, responseDTOs);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}/results")
    public ResponseEntity<List<QuestionResultDTO>> getSurveyResults(@PathVariable Long id) {
        return ResponseEntity.ok(surveyService.getSurveyResults(id));
    }
}
