CREATE TABLE applications(
id BIGINT AUTO_INCREMENT PRIMARY KEY,
creation_date datetime,
candidate_id BIGINT,
FOREIGN KEY(candidate_id) REFERENCES candidates(id));