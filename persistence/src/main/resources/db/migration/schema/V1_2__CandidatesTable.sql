CREATE TABLE candidates(
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255),
  email VARCHAR(255) UNIQUE,
  phone VARCHAR(20) UNIQUE,
  description LONGTEXT,
  referer VARCHAR(255),
  language_skill TINYINT unsigned
);
