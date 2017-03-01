/* QUESTION */
SELECT * FROM AES_QUESTION;
INSERT INTO aes_question VALUES (1, 1, 'Which language are we using?');
INSERT INTO aes_question VALUES (2, 2, 'What is Java?');
INSERT INTO aes_question VALUES (3, 3, 'Order these integers.');
INSERT INTO aes_question VALUES (4, 4, 'Write a program that prints "Hello World"');
INSERT INTO aes_question VALUES (5, 1, 'What is your favorite color?');
INSERT INTO aes_question VALUES (6, 1, 'What is the average velocity of an unladen swallow?');

/* OPTIONS */
SELECT * FROM AES_OPTIONS;
INSERT INTO aes_options VALUES (1, 'Java', 1, 1);
INSERT INTO aes_options VALUES (2, 'C++', 0, 1);
INSERT INTO aes_options VALUES (3, 'Python', 0, 1);
INSERT INTO aes_options VALUES (4, 'Scheme', 0, 1);
INSERT INTO aes_options VALUES (5, 'A programming language', 1, 2);
INSERT INTO aes_options VALUES (6, 'Another word for coffee', 1, 2);
INSERT INTO aes_options VALUES (7, 'Slang for cool', 0, 2);
INSERT INTO aes_options VALUES (8, 'Red', 1, 5);
INSERT INTO aes_options VALUES (9, 'Green', 0, 5);
INSERT INTO aes_options VALUES (10, 'Blue', 0, 5);
INSERT INTO aes_options VALUES (11, '10km/hr', 0, 6);
INSERT INTO aes_options VALUES (12, '12km/hr', 0, 6);
INSERT INTO aes_options VALUES (13, 'African or European?', 1, 6);

/* DRAG AND DROP */
SELECT * FROM aes_drag_drop;
INSERT INTO aes_drag_drop VALUES (1, 3, '42', 3);
INSERT INTO aes_drag_drop VALUES (2, 3, '34', 2);
INSERT INTO aes_drag_drop VALUES (3, 3, '134', 4);
INSERT INTO aes_drag_drop VALUES (4, 3, '7', 1);

/* SNIPPET TEMPLATE */
INSERT INTO aes_snippet_template VALUES (1, 4, 'java', 'testSol.java', 'testSnip.java');

/* QUESTION CATEGORY */
INSERT INTO AES_QUESTION_CATEGORY(QUESTION_ID, CATEGORY_ID) VALUES(1, 1);
INSERT INTO AES_QUESTION_CATEGORY(QUESTION_ID, CATEGORY_ID) VALUES(2, 1);
INSERT INTO AES_QUESTION_CATEGORY(QUESTION_ID, CATEGORY_ID) VALUES(3, 1);
INSERT INTO AES_QUESTION_CATEGORY(QUESTION_ID, CATEGORY_ID) VALUES(4, 1);
INSERT INTO AES_QUESTION_CATEGORY(QUESTION_ID, CATEGORY_ID) VALUES(5, 1);
INSERT INTO AES_QUESTION_CATEGORY(QUESTION_ID, CATEGORY_ID) VALUES(6, 1);