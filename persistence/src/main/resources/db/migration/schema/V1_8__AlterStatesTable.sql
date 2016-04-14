ALTER TABLE states ADD CONSTRAINT fk_application_id FOREIGN KEY (application_id) REFERENCES applications(id);
ALTER TABLE states ADD CONSTRAINT fk_position_id FOREIGN KEY (position_id) REFERENCES positions(id);
ALTER TABLE states ADD CONSTRAINT fk_candidate_id FOREIGN KEY (candidate_id) REFERENCES candidates(id);