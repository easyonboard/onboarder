INSERT INTO LOCATION(ID_LOCATION, LOCATION_ADDRESS, LOCATION_CITY, LOCATION_CONTACT_EMAIL, LOCATION_CONTACT_PHONE, LOCATION_NAME) VALUES (2000, 'Str.Brassai Samuel, nr. 9 ', 'Cluj-Napoca', 'msg.cariere@msg.group', '+40 0733 900 533', 'Brassai');
INSERT INTO LOCATION(ID_LOCATION, LOCATION_ADDRESS, LOCATION_CITY, LOCATION_CONTACT_EMAIL, LOCATION_CONTACT_PHONE, LOCATION_NAME) VALUES (2001, 'Str Regele Ferdinand nr. 10', 'Cluj-Napoca', 'msg.cariere@msg.group', '+40 0733 900 533', 'Ferdinand');
INSERT INTO LOCATION(ID_LOCATION, LOCATION_ADDRESS, LOCATION_CITY, LOCATION_CONTACT_EMAIL, LOCATION_CONTACT_PHONE, LOCATION_NAME) VALUES (2002, 'Str Croitorilor nr. 12-14', 'Cluj-Napoca', 'msg.cariere@msg.group', '+40 0733 900 533', 'Croitorilor');
INSERT INTO LOCATION(ID_LOCATION, LOCATION_ADDRESS, LOCATION_CITY, LOCATION_CONTACT_EMAIL, LOCATION_CONTACT_PHONE, LOCATION_NAME) VALUES (2003, 'B-dul 1 Decembrie 1918, nr. 183', 'Tg. Mureş', 'annamaria.kovacs@msg.group', '+40 0741 660 703 ', 'TgMures');
INSERT INTO LOCATION(ID_LOCATION, LOCATION_ADDRESS, LOCATION_CITY, LOCATION_CONTACT_EMAIL, LOCATION_CONTACT_PHONE, LOCATION_NAME) VALUES (2004, 'Calea Torontalului nr. 1A', 'Timisoara', 'lavinia.brindusa@msg.group', ' +40 0731 308 420', 'Timisoara');

INSERT INTO MEETING_HALL(ID_MEETING_HALL, CAPACITY, FLOOR, HALL_NAME, LOCATION_ID_LOCATION) VALUES (3000, 8, 11, 'Moon I', 2001);
INSERT INTO MEETING_HALL(ID_MEETING_HALL, CAPACITY, FLOOR, HALL_NAME, LOCATION_ID_LOCATION) VALUES (3001, 5, 11, 'Moon II', 2001);

INSERT INTO MEETING_HALL(ID_MEETING_HALL, CAPACITY, FLOOR, HALL_NAME, LOCATION_ID_LOCATION) VALUES (3002, 8, 1, 'Mars-1', 2000);
INSERT INTO MEETING_HALL(ID_MEETING_HALL, CAPACITY, FLOOR, HALL_NAME, LOCATION_ID_LOCATION) VALUES (3003, 10, 3, 'Mercury-3', 2000);
INSERT INTO MEETING_HALL(ID_MEETING_HALL, CAPACITY, FLOOR, HALL_NAME, LOCATION_ID_LOCATION) VALUES (3004, 11, 4, 'Uranus-3', 2000);

INSERT INTO MEETING_HALL(ID_MEETING_HALL, CAPACITY, FLOOR, HALL_NAME, LOCATION_ID_LOCATION) VALUES (3005, 2, 0, 'Moldoveanu', 2002);
INSERT INTO MEETING_HALL(ID_MEETING_HALL, CAPACITY, FLOOR, HALL_NAME, LOCATION_ID_LOCATION) VALUES (3006, 25, 0, 'Hymalaya', 2002);
INSERT INTO MEETING_HALL(ID_MEETING_HALL, CAPACITY, FLOOR, HALL_NAME, LOCATION_ID_LOCATION) VALUES (3007, 15, 0, 'Fuji', 2002);
