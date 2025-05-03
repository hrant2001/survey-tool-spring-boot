CREATE SCHEMA IF NOT EXISTS servey_tool;

DROP TABLE IF EXISTS answer;
DROP TABLE IF EXISTS question;
DROP TABLE IF EXISTS rating;
DROP TABLE IF EXISTS response;
DROP TABLE IF EXISTS survey;

CREATE TABLE survey (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
title VARCHAR(255) NOT NULL
);

CREATE TABLE question (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
text VARCHAR(500) NOT NULL,
survey_id BIGINT,
CONSTRAINT fk_question_survey FOREIGN KEY (survey_id) REFERENCES survey(id)
);

CREATE TABLE response (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
submitted_at TIMESTAMP NOT NULL,
survey_id BIGINT,
CONSTRAINT fk_response_survey FOREIGN KEY (survey_id) REFERENCES survey(id)
);

CREATE TABLE rating (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
label VARCHAR(50) NOT NULL
);

CREATE TABLE answer (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
rating_id BIGINT NOT NULL,
question_id BIGINT,
response_id BIGINT,
CONSTRAINT fk_answer_rating FOREIGN KEY (rating_id) REFERENCES rating(id),
CONSTRAINT fk_answer_question FOREIGN KEY (question_id) REFERENCES question(id),
CONSTRAINT fk_answer_response FOREIGN KEY (response_id) REFERENCES response(id)
);
