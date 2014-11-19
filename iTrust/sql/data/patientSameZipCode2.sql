INSERT INTO patients
(MID, 
firstName,
lastName, 
email,
address1,
address2,
city,
state,
zip,
phone,
eName,
ePhone,
iCName,
iCAddress1,
iCAddress2,
iCCity, 
ICState,
iCZip,
iCPhone,
iCID,
DateOfBirth,
DateOfDeath,
CauseOfDeath,
MotherMID,
FatherMID,
BloodType,
Ethnicity,
Gender,
TopicalNotes
)
VALUES (
2000,
'Andy',
'Programmer',
'andy.programmer@gmail.com',
'344 Bob Street',
'',
'Raleigh',
'NC',
'61820',
'555-555-5555',
'Mr Emergency',
'555-555-5551',
'IC',
'Street1',
'Street2',
'City',
'PA',
'61820-2715',
'555-555-5555',
'1',
'1984-05-19',
'2005-03-10',
'250.10',
1,
0,
'O-',
'Caucasian',
'Male',
'This person is absolutely crazy. Do not touch them.'
)  ON DUPLICATE KEY UPDATE MID = MID;

INSERT INTO users(MID, password, role, sQuestion, sAnswer) 
			VALUES (2000, '30c952fab122c3f9759f02a6d95c3758b246b4fee239957b2d4fee46e26170c4', 'patient', 'how you doin?', 'good')
 ON DUPLICATE KEY UPDATE MID = MID;
 /*password: pw*/

DELETE FROM allergies WHERE PatientID = 2;
INSERT INTO allergies(PatientID,Code,Description, FirstFound) 
	VALUES (2000, '', 'Pollen', '2007-06-05 20:33:58'),
	       (2, '664662530', 'Penicillin', '2007-06-04 20:33:58');
	       
INSERT INTO ndcodes(Code, Description) VALUES
('664662530','Penicillin')
ON DUPLICATE KEY UPDATE Code = Code;

DELETE FROM personalhealthinformation WHERE PatientID = 2000;
INSERT INTO personalhealthinformation
(PatientID,OfficeVisitID,Height,Weight,Smoker,SmokingStatus,BloodPressureN,BloodPressureD,CholesterolHDL,CholesterolLDL,CholesterolTri,HCPID, AsOfDate,OfficeVisitDate,BMI)
VALUES ( 2000,  961, 60,   200,   0, 9,     190,          100,           500,             239,         290,          9000000000, '2007-06-07 20:33:58','2007-06-07',39.06),
	   ( 2,  962, 62,   210,   1, 5,     195,          250,             36,             215,           280,          9000000000, '2007-06-07 20:34:58','2007-06-07',38.41)
	   on duplicate key update OfficeVisitID = OfficeVisitID;




DELETE FROM ovmedication WHERE VisitID = 955;
INSERT INTO ovmedication(NDCode, VisitID, StartDate,EndDate,Dosage,Instructions)
	VALUES ('009042407', 955, '2006-10-10', '2006-10-11', 5, 'Take twice daily'),
		   ('009042407', 955, '2006-10-10', '2006-10-11', 5, 'Take twice daily'),
		   ('647641512', 955, '2006-10-10', '2020-10-11', 5, 'Take twice daily');



