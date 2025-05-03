package com.hrant.survey_tool_spring_boot.service;

import com.hrant.survey_tool_spring_boot.entity.Response;
import com.hrant.survey_tool_spring_boot.repository.ResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResponseService {
    private ResponseRepository responseRepository;

    @Autowired
    public ResponseService(ResponseRepository responseRepository) {
        this.responseRepository = responseRepository;
    }
    public void saveResponses(List<Response> responses) {
        responseRepository.saveAll(responses);
    }
}
