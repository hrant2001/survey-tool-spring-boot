INSERT INTO survey (id, title) VALUES (1, 'Teamwork Evaluation');

INSERT INTO question (text, survey_id) VALUES
('I feel listened to by my colleagues.', 1),
('The distribution of tasks is fair.', 1),
('I can easily collaborate with team members.', 1),
('The team’s objectives are clear.', 1),
('I am satisfied with the overall team atmosphere.', 1);

INSERT INTO survey (id, title) VALUES (2, 'Harassment Awareness');

INSERT INTO question (text, survey_id) VALUES
('I feel safe from harassment in the workplace.', 2),
('The organization has clear policies regarding harassment.', 2),
('I know how to report harassment if it happens.', 2),
('I feel that the company addresses harassment complaints effectively.', 2),
('I believe harassment is taken seriously in the workplace.', 2);

INSERT INTO rating (label) VALUES
('Totally disagree'),
('Disagree'),
('Neutral'),
('Agree'),
('Fully agree');

