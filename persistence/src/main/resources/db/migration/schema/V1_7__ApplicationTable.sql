CREATE TABLE applications(
id BIGINT AUTO_INCREMENT PRIMARY KEY,
candidate_id BIGINT NOT NULL ,
position_id BIGINT NOT NULL,
creation_date datetime,
FOREIGN KEY (candidate_id) REFERENCES candidates(id),
FOREIGN KEY (position_id) REFERENCES positions(id)
);