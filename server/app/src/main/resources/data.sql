INSERT INTO ROLE(id_role, role) VALUES (1, 'ROLE_ADMIN');
INSERT INTO ROLE(id_role, role) VALUES (2, 'ROLE_CONTENT_MANAGER');
INSERT INTO ROLE(id_role, role) VALUES (3, 'ROLE_ABTEILUNGSLEITER');
INSERT INTO ROLE(id_role, role) VALUES (4, 'ROLE_HR');
INSERT INTO ROLE(id_role, role) VALUES (5, 'ROLE_BUDDY');
INSERT INTO ROLE(id_role, role) VALUES (6, 'ROLE_USER');

INSERT INTO APP_USER(id_user, email, msg_mail, name, password, username, role_id_role) VALUES (1, 'admin@db.test', 'admin@msg.test', 'admin', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', 'admin', 1);
INSERT INTO USER_INFORMATION(id_user_information, department, floor, project, start_date, team, user_buddy_id, location_id_location, user_account) VALUES (1, 'GENERAL', null, null, CURRENT_TIMESTAMP, 'Gillardon', null, null, 1);