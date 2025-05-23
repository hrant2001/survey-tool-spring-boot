package com.hrant.survey_tool_spring_boot.repository;

import com.hrant.survey_tool_spring_boot.entity.Response;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponseRepository extends JpaRepository<Response, Long> {
}
