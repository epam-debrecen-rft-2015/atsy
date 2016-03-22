INSERT INTO passwordhistory (userId,password,change_date)
VALUES(1, (select userPwd from users where userId=1), NOW());
INSERT INTO passwordhistory (userId,password,change_date)
VALUES(2, (select userPwd from users where userId=2), NOW());