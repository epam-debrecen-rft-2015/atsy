CREATE TABLE state_flow
(
from_id BIGINT,
to_id BIGINT,
FOREIGN KEY (from_id) REFERENCES states(id),
FOREIGN KEY (to_id) REFERENCES states(id)
)