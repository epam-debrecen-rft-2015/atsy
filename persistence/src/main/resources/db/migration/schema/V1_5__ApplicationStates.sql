CREATE TABLE States(

  stateId BIGINT AUTO_INCREMENT PRIMARY KEY,
  userName VARCHAR(255) UNIQUE NOT NULL  ,
  position VARCHAR(255) NOT NULL ,
  creation_date DATE,
  language_skill TINYINT unsigned,
  description LONGTEXT,
  first_test_result VARCHAR(255),
  offered_money BIGINT,
  claim BIGINT,
  feedback_date DATE,
  state_tpye VARCHAR (50)

)