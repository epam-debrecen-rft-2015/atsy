ALTER TABLE states_history ADD COLUMN position_id BIGINT;
ALTER TABLE states_history ADD COLUMN channel_id BIGINT;
ALTER TABLE states_history ADD CONSTRAINT fk_position_id FOREIGN KEY (position_id) REFERENCES positions(id);
ALTER TABLE states_history ADD CONSTRAINT fk_channel_id FOREIGN KEY (channel_id) REFERENCES channels(id);