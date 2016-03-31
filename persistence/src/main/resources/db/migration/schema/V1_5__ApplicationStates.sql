CREATE TABLE States(

  stateId BIGINT AUTO_INCREMENT PRIMARY KEY,
  candidateId BIGINT NOT NULL  ,
  positionId BIGINT NOT NULL ,
  creation_date DATETIME,
  language_skill TINYINT unsigned,
  description LONGTEXT,
  first_test_result VARCHAR(255),
  offered_money BIGINT,
  claim BIGINT,
  feedback_date DATETIME,
  state_type VARCHAR (50),
  next_state BIGINT

)