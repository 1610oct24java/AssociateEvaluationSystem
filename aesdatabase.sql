/*******************************************************************************
   Drop Constraints
********************************************************************************/
ALTER TABLE  aes_question_tag 
  DROP CONSTRAINT fk_qt_question_id;
    
ALTER TABLE aes_question_tag 
  DROP CONSTRAINT fk_qt_tag_id; 
      
ALTER TABLE aes_question_category
  DROP CONSTRAINT fk_qc_question_id ;

ALTER TABLE aes_question_category
  DROP CONSTRAINT fk_qc_tag_id;
  
ALTER TABLE aes_assessment_drag_drop 
  DROP CONSTRAINT fk_ad_assessment_id;

ALTER TABLE aes_assessment_drag_drop 
  DROP CONSTRAINT fk_ad_drag_drop_id;
  
ALTER TABLE aes_assessment_options
  DROP CONSTRAINT fk_ao_assessment_id;
  
ALTER TABLE aes_assessment_options
  DROP CONSTRAINT fk_ao_option_id;
  
ALTER TABLE aes_users
  DROP CONSTRAINT fk_u_users_role;
  
ALTER TABLE aes_user_trainers
  DROP CONSTRAINT fk_ut_user;
  
ALTER TABLE aes_user_trainers
  DROP CONSTRAINT fk_ut_trainer;  
  
ALTER TABLE aes_file_upload
  DROP CONSTRAINT fk_fu_question;
  
ALTER TABLE aes_file_upload
  DROP CONSTRAINT fk_fu_assessment;
  
ALTER TABLE aes_assessment
  DROP CONSTRAINT fk_a_user;
  
ALTER TABLE aes_assessment
  DROP CONSTRAINT fk_a_template; 

ALTER TABLE aes_security
  DROP CONSTRAINT fk_s_user;

ALTER TABLE aes_assessment_auth
  DROP CONSTRAINT fk_assessment_auth_user_id;
    
ALTER TABLE aes_question 
  DROP CONSTRAINT fk_question_format_id;
  
ALTER TABLE aes_templates
  DROP CONSTRAINT fk_templates_creator_id;

ALTER TABLE aes_options 
  DROP CONSTRAINT fk_o_question_id;

ALTER TABLE aes_drag_drop 
  DROP CONSTRAINT fk_question_id;

ALTER TABLE aes_template_question
  DROP CONSTRAINT fk_template_id;
   
ALTER TABLE aes_template_question
  DROP CONSTRAINT fk_tc_question_id;

ALTER TABLE aes_snippet_template
  DROP CONSTRAINT fk_st_question_id;

ALTER TABLE  aes_snippet_response
  DROP CONSTRAINT fk_sr_snippet_template_id;
  
ALTER TABLE aes_snippet_response
  DROP CONSTRAINT fk_sr_assessment_id;
/*******************************************************************************
   Drop Tables
********************************************************************************/
DROP TABLE aes_formats;
DROP TABLE aes_tags;
DROP TABLE aes_category;
DROP TABLE aes_question_tag;
DROP TABLE aes_question_category;
DROP TABLE aes_assessment_drag_drop;
DROP TABLE aes_assessment_options;
DROP TABLE aes_users;
DROP TABLE aes_user_trainers;
DROP TABLE aes_roles;
DROP TABLE aes_file_upload;
DROP TABLE aes_assessment;
DROP TABLE aes_security;
DROP TABLE aes_options;
DROP TABLE aes_drag_drop;
DROP TABLE aes_template_question;
DROP TABLE aes_assessment_auth;
DROP TABLE aes_snippet_template;
DROP TABLE aes_snippet_response;
DROP TABLE aes_templates;
DROP TABLE aes_question;

/*******************************************************************************
   Drop Sequences
********************************************************************************/
DROP SEQUENCE aes_snippet_template_seq;
DROP SEQUENCE aes_formats_seq;
DROP SEQUENCE aes_tags_seq;
DROP SEQUENCE aes_categories_seq;
DROP SEQUENCE users_seq;
DROP SEQUENCE user_trainers_seq;
DROP SEQUENCE roles_seq;
DROP SEQUENCE file_upload_seq;
DROP SEQUENCE assessment_seq;
DROP SEQUENCE security_seq; 
DROP SEQUENCE aes_question_seq;
DROP SEQUENCE aes_option_seq;
DROP SEQUENCE aes_drag_drop_seq;
DROP SEQUENCE aes_templates_seq;
DROP SEQUENCE aes_template_question_seq;
DROP SEQUENCE aes_assessment_auth_seq;
DROP SEQUENCE aes_snippet_response_seq;

/*******************************************************************************
   Create Tables
********************************************************************************/
CREATE TABLE aes_snippet_response
(
  snippet_response_id NUMBER,
  snippet_template_id NUMBER,
  assessment_id NUMBER,
  CONSTRAINT pk_aes_snippet_response 
    PRIMARY KEY (snippet_response_id)
);

CREATE TABLE aes_snippet_template
(
  snippet_template_id NUMBER,
  snippet_template_url VARCHAR2(255),
  file_type VARCHAR2(255),
  solution_url VARCHAR2(255),
  question_id NUMBER,
  CONSTRAINT pk_aes_snippet_template 
    PRIMARY KEY (snippet_template_id)
);

CREATE TABLE aes_formats(
  format_id   NUMBER,
  format_name VARCHAR2(255),
  CONSTRAINT pk_aes_format 
    PRIMARY KEY(format_id)
);

CREATE TABLE aes_tags(
  tag_id   NUMBER,
  tag_name VARCHAR2(255),
  CONSTRAINT pk_aes_tag 
    PRIMARY KEY(tag_id)
);

CREATE TABLE aes_category(
  category_id   NUMBER,
  category_name VARCHAR2(255),
  CONSTRAINT pk_aes_category 
    PRIMARY KEY(category_id)
);

CREATE TABLE aes_question_tag(
  question_id NUMBER,
  tag_id      NUMBER
);

CREATE TABLE aes_question_category(
	question_id NUMBER,
	tag_id      NUMBER
);

CREATE TABLE aes_assessment_drag_drop(
  assessment_id NUMBER,
  drag_drop_id  NUMBER,
  user_order    NUMBER
);

CREATE TABLE aes_assessment_options(
  assessment_id NUMBER,
  option_id     NUMBER
);

CREATE TABLE aes_users(
  user_id          NUMBER,
  email            VARCHAR2(255) NOT NULL UNIQUE,
  firstname        VARCHAR2(255) NOT NULL,
  lastname         VARCHAR2(255) NOT NULL,
  salesforce       NUMBER,
  recruiter_id     NUMBER,
  role_id          NUMBER NOT NULL,
  date_pass_issued DATE  NOT NULL,
  CONSTRAINT pk_aes_users 
    PRIMARY KEY(user_id)
);

CREATE TABLE aes_user_trainers(
  user_id    NUMBER,
  trainer_id NUMBER
);

CREATE TABLE aes_roles(
  role_id    NUMBER,
  role_title VARCHAR2(255) NOT NULL UNIQUE,
  CONSTRAINT pk_aes_roles 
    PRIMARY KEY(role_id)
);

CREATE TABLE aes_file_upload(
  file_id       NUMBER,
  file_url      VARCHAR2(255),
  grade         NUMBER,
  question_id   NUMBER,
  assessment_id NUMBER,
  CONSTRAINT pk_aes_file_upload 
    PRIMARY KEY(file_id)
);

CREATE TABLE aes_assessment(
  assessment_id      NUMBER,
  user_id            NUMBER NOT NULL,
  grade              NUMBER,
  time_limit         NUMBER,
  created_timestamp  DATE NOT NULL,
  finished_timestamp DATE,
  template_id        NUMBER,
  CONSTRAINT pk_aes_assessment 
    PRIMARY KEY(assessment_id)
);

CREATE TABLE aes_security(
  user_id   NUMBER,
  pass_word VARCHAR2(255) NOT NULL,
  valid     NUMBER,
  CONSTRAINT check_valid 
    CHECK (VALID IN(0,1)),
  CONSTRAINT pk_aes_security 
    PRIMARY KEY(user_id)    
);

CREATE TABLE aes_template_question(
  template_id NUMBER,
  question_id NUMBER,
  weight      NUMBER
);

CREATE TABLE aes_templates(
  template_id      NUMBER,
  create_timestamp TIMESTAMP,
  creator_id       NUMBER,
  CONSTRAINT pk_aes_templates
    PRIMARY KEY (template_id)
);

CREATE TABLE aes_drag_drop(
  drag_drop_id   NUMBER NOT NULL,
  question_id    NUMBER,
  drag_drop_text VARCHAR2(255),
  correct_order  NUMBER,
  CONSTRAINT pk_aes_drag_drop 
    PRIMARY KEY (drag_drop_id)
);

CREATE TABLE aes_question (
  question_id        NUMBER,
  question_format_id NUMBER,
  question_text      VARCHAR2(255),  
  CONSTRAINT pk_aes_question 
    PRIMARY KEY (question_id)
);

CREATE TABLE aes_options (
  option_id   NUMBER,
  option_text VARCHAR2(255),
  correct     NUMBER,
  question_id NUMBER,
  CONSTRAINT check_correct 
    CHECK (correct IN(0,1)),  
  CONSTRAINT pk_aes_option 
    PRIMARY KEY (option_id)
);
  
CREATE TABLE aes_assessment_auth (
  assessment_auth_id NUMBER,
  url_auth VARCHAR2(255),
  url_assessment VARCHAR2(255),
  user_id NUMBER, 
  CONSTRAINT pk_assessment_auth
    PRIMARY KEY (assessment_auth_id)
);  

/*******************************************************************************
   Create Sequences
********************************************************************************/
CREATE SEQUENCE aes_snippet_response_seq
MINVALUE 1
START WITH 1 
INCREMENT BY 1 
CACHE 25;
/

CREATE SEQUENCE aes_snippet_template_seq
  MINVALUE 1
  START WITH 1 
  INCREMENT BY 1 
  CACHE 25;
/
CREATE SEQUENCE aes_formats_seq
  MINVALUE 1
  START WITH 1 
  INCREMENT BY 1 
  CACHE 25;
/
CREATE SEQUENCE aes_tags_seq
  MINVALUE 1
  START WITH 1
  INCREMENT BY 1 
  CACHE 25;
/ 
CREATE SEQUENCE aes_categories_seq
  MINVALUE 1
  START WITH 1
  INCREMENT BY 1 
  CACHE 25;
/ 
 CREATE SEQUENCE users_seq
  MINVALUE 1
  START WITH 1
  INCREMENT BY 1 
  CACHE 25;
/
CREATE SEQUENCE user_trainers_seq
  MINVALUE 1
  START WITH 1
  INCREMENT BY 1 
  CACHE 25;
/
CREATE SEQUENCE roles_seq
  MINVALUE 1
  START WITH 1
  INCREMENT BY 1 
  CACHE 25;
/
CREATE SEQUENCE file_upload_seq
  MINVALUE 1
  START WITH 1
  INCREMENT BY 1 
  CACHE 25;
/
CREATE SEQUENCE assessment_seq
  MINVALUE 1
  START WITH 1
  INCREMENT BY 1 
  CACHE 25;
/
CREATE SEQUENCE security_seq
  MINVALUE 1
  START WITH 1
  INCREMENT BY 1 
  CACHE 25;
/
CREATE SEQUENCE AES_TEMPLATE_QUESTION_SEQ
  MINVALUE 1
  START WITH 1
  INCREMENT BY 1 
  CACHE 25;
/
CREATE SEQUENCE aes_templates_seq
  MINVALUE 1
  START WITH 1
  INCREMENT BY 1 
  CACHE 25;
/
CREATE SEQUENCE aes_drag_drop_seq
  MINVALUE 1
  START WITH 1
  INCREMENT BY 1 
  CACHE 25;
/ 
CREATE SEQUENCE aes_question_seq
  MINVALUE 1
  START WITH 1
  INCREMENT BY 1 
  CACHE 25;
/
CREATE SEQUENCE aes_option_seq
  MINVALUE 1
  START WITH 1
  INCREMENT BY 1 
  CACHE 25;
/
CREATE SEQUENCE aes_assessment_auth_seq
  MINVALUE 1
  START WITH 1
  INCREMENT BY 1 
  CACHE 25;
/  

/*******************************************************************************
   Create Constraints
********************************************************************************/
ALTER TABLE  aes_snippet_response
  ADD CONSTRAINT fk_sr_snippet_template_id
  FOREIGN KEY(snippet_template_id) 
  REFERENCES aes_snippet_template(snippet_template_id);
  
ALTER TABLE aes_snippet_response
  ADD CONSTRAINT fk_sr_assessment_id
  FOREIGN KEY(assessment_id) 
  REFERENCES aes_assessment(assessment_id);

ALTER TABLE aes_snippet_template
  ADD CONSTRAINT fk_st_question_id
  FOREIGN KEY(question_id) 
  REFERENCES aes_question(question_id);

ALTER TABLE aes_question_tag 
  ADD CONSTRAINT fk_qt_question_id
  FOREIGN KEY(question_id) 
  REFERENCES aes_question(question_id)
  ON DELETE CASCADE;
    
ALTER TABLE aes_question_tag 
  ADD CONSTRAINT fk_qt_tag_id 
  FOREIGN KEY(tag_id) 
  REFERENCES aes_tags(tag_id)
  ON DELETE CASCADE;
    
ALTER TABLE aes_question_category
  ADD CONSTRAINT fk_qc_question_id 
  FOREIGN KEY(question_id)
  REFERENCES aes_question(question_id)
  ON DELETE CASCADE;

ALTER TABLE aes_question_category
  ADD CONSTRAINT fk_qc_tag_id
  FOREIGN KEY(tag_id)
  REFERENCES aes_tags(tag_id)
  ON DELETE CASCADE;
  
ALTER TABLE aes_assessment_drag_drop 
  ADD CONSTRAINT fk_ad_assessment_id
  FOREIGN KEY(assessment_id)
  REFERENCES aes_assessment(assessment_id)
  ON DELETE CASCADE;

ALTER TABLE aes_assessment_drag_drop 
  ADD CONSTRAINT fk_ad_drag_drop_id
  FOREIGN KEY(drag_drop_id)
  REFERENCES aes_drag_drop(drag_drop_id)
  ON DELETE CASCADE;
  
ALTER TABLE aes_assessment_options
  ADD CONSTRAINT fk_ao_assessment_id
  FOREIGN KEY(assessment_id)
  REFERENCES aes_assessment(assessment_id)
  ON DELETE CASCADE;
  
ALTER TABLE aes_assessment_options
  ADD CONSTRAINT fk_ao_option_id
  FOREIGN KEY(option_id)
  REFERENCES aes_options(option_id)
  ON DELETE CASCADE;
  
ALTER TABLE aes_users
  ADD CONSTRAINT fk_u_users_role 
  FOREIGN KEY (role_id) 
  REFERENCES aes_roles(role_id)
  ON DELETE CASCADE;
  
ALTER TABLE aes_user_trainers
  ADD CONSTRAINT fk_ut_user 
  FOREIGN KEY (user_id)
  REFERENCES aes_users (user_id)
  ON DELETE CASCADE;
  
ALTER TABLE aes_user_trainers
  ADD CONSTRAINT fk_ut_trainer 
  FOREIGN KEY(trainer_id) 
  REFERENCES aes_users(user_id)
  ON DELETE CASCADE;  
  
ALTER TABLE aes_file_upload
  ADD CONSTRAINT fk_fu_question 
  FOREIGN KEY(question_id) 
  REFERENCES aes_question(question_id)
  ON DELETE CASCADE;
  
ALTER TABLE aes_file_upload
  ADD CONSTRAINT fk_fu_assessment 
  FOREIGN KEY(assessment_id) 
  REFERENCES aes_assessment(assessment_id)
  ON DELETE CASCADE;
  
ALTER TABLE aes_assessment
  ADD CONSTRAINT fk_a_user 
  FOREIGN KEY(user_id) 
  REFERENCES aes_users(user_id)
  ON DELETE CASCADE;
  
ALTER TABLE aes_assessment
  ADD CONSTRAINT fk_a_template 
  FOREIGN KEY (template_id) 
  REFERENCES aes_templates(template_id)
  ON DELETE CASCADE;  

ALTER TABLE aes_security
  ADD CONSTRAINT fk_s_user
  FOREIGN KEY (user_id)
  REFERENCES aes_users(user_id)
  ON DELETE CASCADE;
  
ALTER TABLE aes_template_question
  ADD CONSTRAINT fk_template_id
  FOREIGN KEY (template_id)
  REFERENCES aes_templates(template_id)
  ON DELETE CASCADE;
    
ALTER TABLE aes_template_question
  ADD CONSTRAINT fk_tc_question_id
  FOREIGN KEY (question_id)
  REFERENCES aes_question(question_id)
  ON DELETE CASCADE;
    
ALTER TABLE aes_templates
  ADD CONSTRAINT fk_templates_creator_id
  FOREIGN KEY (creator_id)
  REFERENCES aes_users(user_id)
  ON DELETE CASCADE;

ALTER TABLE aes_question 
  ADD CONSTRAINT fk_question_format_id 
  FOREIGN KEY (question_format_id)
  REFERENCES aes_formats(format_id)
  ON DELETE CASCADE;

ALTER TABLE aes_options 
  ADD CONSTRAINT fk_o_question_id
  FOREIGN KEY (question_id)
  REFERENCES aes_question(question_id)
  ON DELETE CASCADE;

ALTER TABLE aes_drag_drop 
  ADD CONSTRAINT fk_question_id
  FOREIGN KEY (question_id) 
  REFERENCES aes_question(question_id)
  ON DELETE CASCADE;
  
ALTER TABLE aes_assessment_auth
  ADD CONSTRAINT fk_assessment_auth_user_id
  FOREIGN KEY (user_id)
  REFERENCES aes_users(user_id)
  ON DELETE CASCADE;


