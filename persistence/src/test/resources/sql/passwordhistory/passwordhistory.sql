INSERT INTO users(user_name, user_pwd) VALUES('Test2', 'pass4');

INSERT INTO password_history (users_id, password, change_date) VALUES
((select id from users where user_name = 'Dev'), 'password_history1', NOW()),
((select id from users where user_name = 'Dev'), 'password_history2', NOW());