INSERT INTO password_history (users_id,password,change_date)
VALUES(1, (select user_pwd from users where id=1), NOW());
INSERT INTO password_history (users_id,password,change_date)
VALUES(2, (select user_pwd from users where id=2), NOW());