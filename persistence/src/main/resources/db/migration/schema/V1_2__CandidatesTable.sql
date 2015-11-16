CREATE TABLE Candidates(
  candidateId BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255),
  email VARCHAR(255) ,
  phone VARCHAR(12),
  description LONGTEXT,
  referer VARCHAR(255),
  language_skill TINYINT unsigned
);