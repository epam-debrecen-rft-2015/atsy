CREATE TABLE password_history(
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  users_id BIGINT,
  FOREIGN KEY (users_id) REFERENCES users(id),
  password VARCHAR(255) NOT NULL,
  change_date DATETIME NOT NULL
);