CREATE TABLE PasswordHistory(
  changeId BIGINT AUTO_INCREMENT PRIMARY KEY,
  userId BIGINT,
  FOREIGN KEY (userId) REFERENCES users(userId),
  password VARCHAR(255) NOT NULL,
  change_date DATE NOT NULL
);