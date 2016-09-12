CREATE TABLE candidates(
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255),
  email VARCHAR(255),
  phone VARCHAR(12),
  description LONGTEXT,
  referer VARCHAR(255),
  language_skill TINYINT unsigned,
  cv_file_name VARCHAR(255) UNIQUE
);
