ALTER TABLE states_history MODIFY COLUMN creation_date DATETIME NOT NULL;
ALTER TABLE states_history MODIFY COLUMN first_test_result TINYINT(3) UNSIGNED;