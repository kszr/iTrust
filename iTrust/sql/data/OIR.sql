DELETE FROM obstetricsinitializationrecords;

INSERT INTO obstetricsinitializationrecords(patientID, creationDate, lastMensturalPeriod) VALUES
(301, CURDATE()-INTERVAL 300 DAY, CURDATE()-INTERVAL 400 DAY);

INSERT INTO obstetricsinitializationrecords(patientID, creationDate, lastMensturalPeriod) VALUES
(301, CURDATE()-INTERVAL 980 DAY, CURDATE()-INTERVAL 1055 DAY);

INSERT INTO obstetricsinitializationrecords(patientID, creationDate, lastMensturalPeriod) VALUES
(301, CURDATE()-INTERVAL 600 DAY, CURDATE()-INTERVAL 660 DAY);

INSERT INTO obstetricsinitializationrecords(patientID, creationDate, lastMensturalPeriod) VALUES
(1, CURDATE()-INTERVAL 100 DAY, CURDATE()-INTERVAL 140 DAY);

