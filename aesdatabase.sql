/*******************************************************************************
   12/28/2016 : Added primary keys and sequences to AES_ASSESSMENT_OPTIONS and AES_TEMPLATE_QUESTION
********************************************************************************/


/*******************************************************************************
   Drop Constraints
********************************************************************************/

ALTER TABLE aes_question_tag
  DROP CONSTRAINT fk_qt_question_id;

ALTER TABLE aes_question_tag
  DROP CONSTRAINT fk_qt_tag_id;

ALTER TABLE aes_question_category
  DROP CONSTRAINT fk_qc_question_id;

ALTER TABLE aes_question_category
  DROP CONSTRAINT fk_qc_category_id;

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

ALTER TABLE aes_snippet_response
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
DROP SEQUENCE aes_users_seq;
DROP SEQUENCE aes_roles_seq;
DROP SEQUENCE aes_file_upload_seq;
DROP SEQUENCE aes_assessment_seq;
DROP SEQUENCE aes_question_seq;
DROP SEQUENCE aes_option_seq;
DROP SEQUENCE aes_drag_drop_seq;
DROP SEQUENCE aes_templates_seq;
DROP SEQUENCE aes_assessment_auth_seq;
CREATE SEQUENCE aes_assessment_drag_drop_seq;
CREATE SEQUENCE aes_template_question_seq;

/*******************************************************************************
   Create Tables
********************************************************************************/

CREATE TABLE aes_snippet_response
(
  snippet_template_id   NUMBER NOT NULL,
  assessment_id         NUMBER NOT NULL
);

CREATE TABLE aes_snippet_template
(
  snippet_template_id    NUMBER,
  question_id            NUMBER NOT NULL,
  file_type              VARCHAR2 ( 255 ) NOT NULL,
  snippet_template_url   VARCHAR2 ( 255 ) NOT NULL,
  solution_url           VARCHAR2 ( 255 ) NOT NULL,
  CONSTRAINT pk_aes_snippet_template PRIMARY KEY ( snippet_template_id )
);

CREATE TABLE aes_formats
(
  format_id     NUMBER,
  format_name   VARCHAR2 ( 255 ) NOT NULL UNIQUE,
  CONSTRAINT pk_aes_format PRIMARY KEY ( format_id )
);

CREATE TABLE aes_tags
(
  tag_id     NUMBER,
  tag_name   VARCHAR2 ( 255 ) NOT NULL UNIQUE,
  CONSTRAINT pk_aes_tag PRIMARY KEY ( tag_id )
);

CREATE TABLE aes_category
(
  category_id     NUMBER,
  category_name   VARCHAR2 ( 255 ) NOT NULL UNIQUE,
  CONSTRAINT pk_aes_category PRIMARY KEY ( category_id )
);

CREATE TABLE aes_question_tag
(
  question_id   NUMBER NOT NULL,
  tag_id        NUMBER NOT NULL
);

CREATE TABLE aes_question_category
(
  question_id   NUMBER NOT NULL,
  category_id   NUMBER NOT NULL
);

CREATE TABLE aes_assessment_drag_drop
(
  assessment_drag_drop_id 	NUMBER,
  assessment_id   			NUMBER NOT NULL,
  drag_drop_id    			NUMBER NOT NULL,
  user_order      			NUMBER,
  CONSTRAINT pk_aes_assessment_drag_drop PRIMARY KEY ( assessment_drag_drop_id )
);

CREATE TABLE aes_assessment_options
(
  assessment_id   NUMBER NOT NULL,
  option_id       NUMBER NOT NULL
);

CREATE TABLE aes_users
(
  user_id            NUMBER,
  email              VARCHAR2 ( 255 ) NOT NULL UNIQUE,
  firstname          VARCHAR2 ( 255 ) NOT NULL,
  lastname           VARCHAR2 ( 255 ) NOT NULL,
  salesforce         NUMBER,
  recruiter_id       NUMBER,
  role_id            NUMBER NOT NULL,
  date_pass_issued   DATE NOT NULL,
  CONSTRAINT pk_aes_users PRIMARY KEY ( user_id )
);

CREATE TABLE aes_user_trainers
(
  user_id      NUMBER NOT NULL,
  trainer_id   NUMBER NOT NULL
);

CREATE TABLE aes_roles
(
  role_id      NUMBER,
  role_title   VARCHAR2 ( 255 ) NOT NULL UNIQUE,
  CONSTRAINT pk_aes_roles PRIMARY KEY ( role_id )
);

CREATE TABLE aes_file_upload
(
  file_id         NUMBER,
  file_url        VARCHAR2 ( 255 ) NOT NULL UNIQUE,
  grade           NUMBER,
  question_id     NUMBER NOT NULL,
  assessment_id   NUMBER NOT NULL,
  CONSTRAINT pk_aes_file_upload PRIMARY KEY ( file_id )
);

CREATE TABLE aes_assessment
(
  assessment_id        NUMBER,
  user_id              NUMBER NOT NULL,
  grade                NUMBER,
  time_limit           NUMBER NOT NULL,
  created_timestamp    DATE,
  finished_timestamp   DATE,
  template_id          NUMBER,
  CONSTRAINT pk_aes_assessment PRIMARY KEY ( assessment_id )
);

CREATE TABLE aes_security
(
  user_id     NUMBER,
  pass_word   VARCHAR2 ( 255 ) NOT NULL,
  valid       NUMBER NOT NULL,
  CONSTRAINT check_valid CHECK ( valid IN ( 0, 1 ) ),
  CONSTRAINT pk_aes_security PRIMARY KEY ( user_id )
);

CREATE TABLE aes_template_question
(
  template_question_id 	NUMBER,
  template_id   		NUMBER NOT NULL,
  question_id  			NUMBER NOT NULL,
  weight        		NUMBER DEFAULT 1,
  CONSTRAINT pk_aes_template_question PRIMARY KEY ( template_question_id )
);

CREATE TABLE aes_templates
(
  template_id        NUMBER,
  create_timestamp   TIMESTAMP NOT NULL,
  creator_id         NUMBER NOT NULL,
  CONSTRAINT pk_aes_templates PRIMARY KEY ( template_id )
);

CREATE TABLE aes_drag_drop
(
  drag_drop_id     NUMBER NOT NULL,
  question_id      NUMBER NOT NULL,
  drag_drop_text   VARCHAR2 ( 255 ) NOT NULL,
  correct_order    NUMBER NOT NULL,
  CONSTRAINT pk_aes_drag_drop PRIMARY KEY ( drag_drop_id )
);

CREATE TABLE aes_question
(
  question_id          NUMBER,
  question_format_id   NUMBER NOT NULL,
  question_text        VARCHAR2 ( 255 ) NOT NULL,
  CONSTRAINT pk_aes_question PRIMARY KEY ( question_id )
);

CREATE TABLE aes_options
(
  option_id     NUMBER,
  option_text   VARCHAR2 ( 255 ) NOT NULL,
  correct       NUMBER NOT NULL,
  question_id   NUMBER NOT NULL,
  CONSTRAINT check_correct CHECK ( correct IN ( 0, 1 ) ),
  CONSTRAINT pk_aes_option PRIMARY KEY ( option_id )
);

CREATE TABLE aes_assessment_auth
(
  assessment_auth_id   NUMBER,
  user_id              NUMBER NOT NULL,
  url_auth             VARCHAR2 ( 255 ) NOT NULL,
  url_assessment       VARCHAR2 ( 255 ) NOT NULL,
  CONSTRAINT pk_assessment_auth PRIMARY KEY ( assessment_auth_id )
);

/*******************************************************************************
   Create Sequences
********************************************************************************/

CREATE SEQUENCE aes_snippet_template_seq MINVALUE 1
                                         START WITH 1
                                         INCREMENT BY 1
                                         NOCACHE;
/

CREATE SEQUENCE aes_formats_seq MINVALUE 1 START WITH 1 INCREMENT BY 1 NOCACHE;
/

CREATE SEQUENCE aes_tags_seq MINVALUE 1 START WITH 1 INCREMENT BY 1 NOCACHE;
/

CREATE SEQUENCE aes_categories_seq MINVALUE 1
                                   START WITH 1
                                   INCREMENT BY 1
                                   NOCACHE;
/

CREATE SEQUENCE aes_users_seq MINVALUE 1 START WITH 1 INCREMENT BY 1 NOCACHE;
/

CREATE SEQUENCE aes_roles_seq MINVALUE 1 START WITH 1 INCREMENT BY 1 NOCACHE;
/

CREATE SEQUENCE aes_file_upload_seq MINVALUE 1
                                    START WITH 1
                                    INCREMENT BY 1
                                    NOCACHE;
/

CREATE SEQUENCE aes_assessment_seq MINVALUE 1
                                   START WITH 1
                                   INCREMENT BY 1
                                   NOCACHE;
/

CREATE SEQUENCE aes_templates_seq MINVALUE 1
                                  START WITH 1
                                  INCREMENT BY 1
                                  NOCACHE;
/

CREATE SEQUENCE aes_drag_drop_seq MINVALUE 1
                                  START WITH 1
                                  INCREMENT BY 1
                                  NOCACHE;
/

CREATE SEQUENCE aes_question_seq MINVALUE 1 START WITH 1 INCREMENT BY 1 NOCACHE;
/

CREATE SEQUENCE aes_option_seq MINVALUE 1 START WITH 1 INCREMENT BY 1 NOCACHE;
/

CREATE SEQUENCE aes_assessment_auth_seq MINVALUE 1
                                        START WITH 1
                                        INCREMENT BY 1
                                        NOCACHE;
/

CREATE SEQUENCE aes_assessment_drag_drop_seq MINVALUE 1 START WITH 1 INCREMENT BY 1 NOCACHE;
/

CREATE SEQUENCE aes_template_question_seq MINVALUE 1 START WITH 1 INCREMENT BY 1 NOCACHE;
/

/*******************************************************************************
   Create Constraints
********************************************************************************/

ALTER TABLE aes_snippet_response
  ADD CONSTRAINT fk_sr_snippet_template_id FOREIGN KEY ( snippet_template_id )
      REFERENCES aes_snippet_template ( snippet_template_id );

ALTER TABLE aes_snippet_response
  ADD CONSTRAINT fk_sr_assessment_id FOREIGN KEY ( assessment_id )
      REFERENCES aes_assessment ( assessment_id );

ALTER TABLE aes_snippet_template
  ADD CONSTRAINT fk_st_question_id FOREIGN KEY ( question_id )
      REFERENCES aes_question ( question_id );

ALTER TABLE aes_question_tag
  ADD CONSTRAINT fk_qt_question_id FOREIGN KEY ( question_id )
      REFERENCES aes_question ( question_id ) ON DELETE CASCADE;

ALTER TABLE aes_question_tag
  ADD CONSTRAINT fk_qt_tag_id FOREIGN KEY ( tag_id )
      REFERENCES aes_tags ( tag_id ) ON DELETE CASCADE;

ALTER TABLE aes_question_category
  ADD CONSTRAINT fk_qc_question_id FOREIGN KEY ( question_id )
      REFERENCES aes_question ( question_id ) ON DELETE CASCADE;

ALTER TABLE aes_question_category
  ADD CONSTRAINT fk_qc_category_id FOREIGN KEY ( category_id )
      REFERENCES aes_category ( category_id ) ON DELETE CASCADE;

ALTER TABLE aes_assessment_drag_drop
  ADD CONSTRAINT fk_ad_assessment_id FOREIGN KEY ( assessment_id )
      REFERENCES aes_assessment ( assessment_id ) ON DELETE CASCADE;

ALTER TABLE aes_assessment_drag_drop
  ADD CONSTRAINT fk_ad_drag_drop_id FOREIGN KEY ( drag_drop_id )
      REFERENCES aes_drag_drop ( drag_drop_id ) ON DELETE CASCADE;

ALTER TABLE aes_assessment_options
  ADD CONSTRAINT fk_ao_assessment_id FOREIGN KEY ( assessment_id )
      REFERENCES aes_assessment ( assessment_id ) ON DELETE CASCADE;

ALTER TABLE aes_assessment_options
  ADD CONSTRAINT fk_ao_option_id FOREIGN KEY ( option_id )
      REFERENCES aes_options ( option_id ) ON DELETE CASCADE;

ALTER TABLE aes_users
  ADD CONSTRAINT fk_u_users_role FOREIGN KEY ( role_id )
      REFERENCES aes_roles ( role_id ) ON DELETE CASCADE;

ALTER TABLE aes_user_trainers
  ADD CONSTRAINT fk_ut_user FOREIGN KEY ( user_id )
      REFERENCES aes_users ( user_id ) ON DELETE CASCADE;

ALTER TABLE aes_user_trainers
  ADD CONSTRAINT fk_ut_trainer FOREIGN KEY ( trainer_id )
      REFERENCES aes_users ( user_id ) ON DELETE CASCADE;

ALTER TABLE aes_file_upload
  ADD CONSTRAINT fk_fu_question FOREIGN KEY ( question_id )
      REFERENCES aes_question ( question_id ) ON DELETE CASCADE;

ALTER TABLE aes_file_upload
  ADD CONSTRAINT fk_fu_assessment FOREIGN KEY ( assessment_id )
      REFERENCES aes_assessment ( assessment_id ) ON DELETE CASCADE;

ALTER TABLE aes_assessment
  ADD CONSTRAINT fk_a_user FOREIGN KEY ( user_id )
      REFERENCES aes_users ( user_id ) ON DELETE CASCADE;

ALTER TABLE aes_assessment
  ADD CONSTRAINT fk_a_template FOREIGN KEY ( template_id )
      REFERENCES aes_templates ( template_id ) ON DELETE CASCADE;

ALTER TABLE aes_security
  ADD CONSTRAINT fk_s_user FOREIGN KEY ( user_id )
      REFERENCES aes_users ( user_id ) ON DELETE CASCADE;

ALTER TABLE aes_template_question
  ADD CONSTRAINT fk_template_id FOREIGN KEY ( template_id )
      REFERENCES aes_templates ( template_id ) ON DELETE CASCADE;

ALTER TABLE aes_template_question
  ADD CONSTRAINT fk_tc_question_id FOREIGN KEY ( question_id )
      REFERENCES aes_question ( question_id ) ON DELETE CASCADE;

ALTER TABLE aes_templates
  ADD CONSTRAINT fk_templates_creator_id FOREIGN KEY ( creator_id )
      REFERENCES aes_users ( user_id ) ON DELETE CASCADE;

ALTER TABLE aes_question
  ADD CONSTRAINT fk_question_format_id FOREIGN KEY ( question_format_id )
      REFERENCES aes_formats ( format_id ) ON DELETE CASCADE;

ALTER TABLE aes_options
  ADD CONSTRAINT fk_o_question_id FOREIGN KEY ( question_id )
      REFERENCES aes_question ( question_id ) ON DELETE CASCADE;

ALTER TABLE aes_drag_drop
  ADD CONSTRAINT fk_question_id FOREIGN KEY ( question_id )
      REFERENCES aes_question ( question_id ) ON DELETE CASCADE;

ALTER TABLE aes_assessment_auth
  ADD CONSTRAINT fk_assessment_auth_user_id FOREIGN KEY ( user_id )
      REFERENCES aes_users ( user_id ) ON DELETE CASCADE;

/*******************************************************************************
   Create Constraints
********************************************************************************/

ALTER TABLE aes_snippet_response
  ADD CONSTRAINT un_st_a UNIQUE ( snippet_template_id, assessment_id );

ALTER TABLE aes_question_tag
  ADD CONSTRAINT un_q_t UNIQUE ( question_id, tag_id );

ALTER TABLE aes_assessment_drag_drop
  ADD CONSTRAINT un_a_dd UNIQUE ( assessment_id, drag_drop_id );

ALTER TABLE aes_assessment_options
  ADD CONSTRAINT un_a_o UNIQUE ( assessment_id, option_id );

ALTER TABLE aes_user_trainers
  ADD CONSTRAINT un_u_t UNIQUE ( user_id, trainer_id );

ALTER TABLE aes_template_question
  ADD CONSTRAINT un_t_q UNIQUE ( template_id, question_id );
