INSERT INTO airport (id, airportcode, city, country, name)
VALUES (4, 'AMS', 'Amsterdam', 'The Netherlands', 'Schiphol');
INSERT INTO airport (id, airportcode, city, country, name) VALUES (5, 'DTW', 'Detroid', 'USA', 'Detroid City');
INSERT INTO airport (id, airportcode, city, country, name)
VALUES (8, 'NRT', 'Tokyo', 'Japan', 'Narita International Airport');
INSERT INTO airport (id, airportcode, city, country, name) VALUES (12, 'SYD', 'Sydney', 'Australia', 'Kingsford Smith');
INSERT INTO airport (id, airportcode, city, country, name)
VALUES (13, 'LAX', 'Los Angeles', 'USA', 'Los Angeles International');
INSERT INTO airport (id, airportcode, city, country, name)
VALUES (19, 'FRA', 'Frankfurt', 'Germany', 'Frankfurt International Airport');
INSERT INTO airport (id, airportcode, city, country, name)
VALUES (20, 'ORD', 'Chicago', 'USA', 'Chicago O''hare International');
INSERT INTO airport (id, airportcode, city, country, name) VALUES (23, 'LHR', 'London', 'UK', 'London Heathrow');
INSERT INTO airport (id, airportcode, city, country, name)
VALUES (27, 'JFK', 'New York', 'USA', 'John F. Kennedy International');
INSERT INTO airport (id, airportcode, city, country, name)
VALUES (29, 'SIN', 'Singapore', 'Singapore', 'Changi Airport');

INSERT INTO airline (id, name) VALUES (2, 'SkyTeam');
INSERT INTO airline (id, name) VALUES (10, 'oneworld');
INSERT INTO airline (id, name) VALUES (17, 'North Star');

INSERT INTO airplane (id, capacity, model, serialnr) VALUES (3, 519, 'A380', '12345');
INSERT INTO airplane (id, capacity, model, serialnr) VALUES (7, 416, '747', '54321');
INSERT INTO airplane (id, capacity, model, serialnr) VALUES (11, 519, 'A380', '23451');
INSERT INTO airplane (id, capacity, model, serialnr) VALUES (15, 416, '747', '43215');
INSERT INTO airplane (id, capacity, model, serialnr) VALUES (18, 519, 'A380', '34512');
INSERT INTO airplane (id, capacity, model, serialnr) VALUES (22, 416, '747', '32154');

INSERT INTO flight (id, arrivalDate, arrivalTime, departureDate, departureTime, flightnr, airline_id, airplane_id, destination_id, origin_id)
VALUES (1, '2015-06-25', '09:00:00', '2009-08-06', '19:10:00', 'NW 36', 2, 3, 4, 5);
INSERT INTO flight (id, arrivalDate, arrivalTime, departureDate, departureTime, flightnr, airline_id, airplane_id, destination_id, origin_id)
VALUES (6, '2015-06-25', '13:45:00', '2009-08-06', '15:05:00', 'NW 96', 2, 7, 5, 8);
INSERT INTO flight (id, arrivalDate, arrivalTime, departureDate, departureTime, flightnr, airline_id, airplane_id, destination_id, origin_id)
VALUES (9, '2015-06-25', '06:15:00', '2009-08-05', '22:30:00', 'QF 12', 10, 11, 12, 13);
INSERT INTO flight (id, arrivalDate, arrivalTime, departureDate, departureTime, flightnr, airline_id, airplane_id, destination_id, origin_id)
VALUES (14, '2015-06-25', '06:55:00', '2009-08-06', '21:55:00', 'QF 21', 10, 15, 8, 12);
INSERT INTO flight (id, arrivalDate, arrivalTime, departureDate, departureTime, flightnr, airline_id, airplane_id, destination_id, origin_id)
VALUES (16, '2015-06-25', '05:45:00', '2009-08-06', '14:30:00', 'UA 944', 17, 18, 19, 20);
INSERT INTO flight (id, arrivalDate, arrivalTime, departureDate, departureTime, flightnr, airline_id, airplane_id, destination_id, origin_id)
VALUES (21, '2015-06-25', '07:30:00', '2009-08-06', '12:59:00', 'UA 934', 17, 22, 23, 13);
INSERT INTO flight (id, arrivalDate, arrivalTime, departureDate, departureTime, flightnr, airline_id, airplane_id, destination_id, origin_id)
VALUES (24, '2015-06-25', '07:40:00', '2015-06-25', '07:15:00', 'NW 8445', 2, 3, 23, 4);
INSERT INTO flight (id, arrivalDate, arrivalTime, departureDate, departureTime, flightnr, airline_id, airplane_id, destination_id, origin_id)
VALUES (25, '2015-06-25', '12:21:00', '2015-06-25', '12:05:00', 'NW 1689', 2, 7, 20, 5);
INSERT INTO flight (id, arrivalDate, arrivalTime, departureDate, departureTime, flightnr, airline_id, airplane_id, destination_id, origin_id)
VALUES (26, '2015-06-25', '23:39:00', '2015-06-25', '15:00:00', 'QF 3101', 10, 11, 27, 13);
INSERT INTO flight (id, arrivalDate, arrivalTime, departureDate, departureTime, flightnr, airline_id, airplane_id, destination_id, origin_id)
VALUES (28, '2015-06-25', '17:15:00', '2015-06-25', '11:05:00', 'QF 4022', 10, 15, 29, 8);
INSERT INTO flight (id, arrivalDate, arrivalTime, departureDate, departureTime, flightnr, airline_id, airplane_id, destination_id, origin_id)
VALUES (30, '2015-06-25', '14:53:00', '2015-06-25', '12:45:00', 'UA 941', 17, 18, 20, 19);
INSERT INTO flight (id, arrivalDate, arrivalTime, departureDate, departureTime, flightnr, airline_id, airplane_id, destination_id, origin_id)
VALUES (31, '2015-06-25', '10:38:00', '2015-06-25', '08:10:00', 'UA 4842', 17, 22, 4, 23);
INSERT INTO flight (id, arrivalDate, arrivalTime, departureDate, departureTime, flightnr, airline_id, airplane_id, destination_id, origin_id)
VALUES (32, '2009-08-07', '10:38:00', '2009-08-07', '08:10:00', 'UA 4842', 17, 22, 4, 23);

-- MySQL/Oracle
--UPDATE hibernate_sequence SET next_val = 1000;

--Derby
UPDATE APP."SEQUENCE"
SET SEQ_COUNT = 1000
WHERE SEQ_NAME = 'SEQ_GEN';




