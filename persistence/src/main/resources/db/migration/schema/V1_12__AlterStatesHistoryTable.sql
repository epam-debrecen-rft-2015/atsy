ALTER TABLE states_history  ADD COLUMN state_id BIGINT;
ALTER TABLE states_history ADD CONSTRAINT fk_state_id FOREIGN KEY (state_id) REFERENCES states(id);
ALTER TABLE states_history DROP COLUMN state_index, DROP COLUMN state_type;