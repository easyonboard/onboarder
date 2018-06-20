INSERT INTO ROLE(id_role, role) VALUES (1, 'ROLE_ADMIN');
INSERT INTO ROLE(id_role, role) VALUES (2, 'ROLE_CONTENT_MANAGER');
INSERT INTO ROLE(id_role, role) VALUES (3, 'ROLE_ABTEILUNGSLEITER');
INSERT INTO ROLE(id_role, role) VALUES (4, 'ROLE_HR');
INSERT INTO ROLE(id_role, role) VALUES (5, 'ROLE_BUDDY');
INSERT INTO ROLE(id_role, role) VALUES (6, 'ROLE_USER');

INSERT INTO APP_USER(id_user, email, msg_mail, name, password, username, role_id_role) VALUES (1, 'admin@db.test', 'admin@msg.test', 'admin', '8C6976E5B5410415BDE908BD4DEE15DFB167A9C873FC4BB8A81F6F2AB448A918', 'admin', 1);