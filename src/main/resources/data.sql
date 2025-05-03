INSERT INTO survey (id, title) VALUES (1, 'Teamwork Evaluation');

INSERT INTO question (text, survey_id) VALUES
('I feel listened to by my colleagues.', 1),
('The distribution of tasks is fair.', 1),
('I can easily collaborate with team members.', 1),
('The team’s objectives are clear.', 1),
('I am satisfied with the overall team atmosphere.', 1);

INSERT INTO rating (label) VALUES
('Totally disagree'),
('Disagree'),
('Neutral'),
('Agree'),
('Fully agree');

