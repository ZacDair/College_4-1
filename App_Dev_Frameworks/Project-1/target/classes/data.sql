-- Populate our Household table with multiple house entries --
INSERT INTO Household (houseID, houseEircode, houseAddress) VALUES
(1, 'T12 EP44', 'Orchard House, Cork'),
(2, 'DK2 E921', 'Meadow View, Dublin'),
(3, 'C90 KE10', 'Rose Cottage, Limerick'),
(4, 'T52 DP62', 'Oak Barn, Cork'),
(5, 'EA5 PK90', 'Lockwood Close, Cork'),
(6, 'K78 C9KA', 'Rousseau View, Dublin');


-- Populate our Person table with multiple people of different ages --
INSERT INTO Person (personID, personName, personAge, personOccupation) VALUES
(1, 'Oliver Queen', 33, 'CEO of Queen Consolidated'),
(2, 'Felicity Smoak', 31, 'IT Expert'),
(3, 'Mia Queen', 10, 'Scholar'),
(4, 'John Diggle', 42, 'Security Consultant'),
(5, 'Lyla Michaels', 38, 'Director of Argus'),
(6, 'J.J Diggle', 8, 'Scholar'),
(7, 'Sara Diggle', 6, 'Scholar'),
(8, 'Steve Rodgers', 87, 'Retired Veteran'),
(9, 'Sharon Carter', 75, 'Author'),
(10, 'Stefan Salvatore', 23, 'Scholar'),
(11, 'Damon Salvatore', 32, 'Bar Owner'),
(12, 'Elena Gilbert', 32, 'Nurse'),
(13, 'Alica Salvatore', 3, 'Pre-School'),
(14, 'Elijah Mikaelson', 67, 'Author'),
(15, 'Klaus Mikaelson', 67, 'Painter'),
(16, 'Haley Mikaelson', 66, 'Nurse');



-- Populate our OccupantRecords junction table with multiple people in multiple houses --
-- Ideally a select statement would be used to find the Foreign Keys --
INSERT INTO OccupantRecords (occupantID, houseID, personID) VALUES
(1, 1, 1),
(2, 1, 2),
(3, 1, 3),
(4, 2, 4),
(5, 2, 5),
(6, 2, 6),
(7, 2, 7),
(8, 3, 8),
(9, 3, 9),
(10, 4, 10),
(11, 5, 11),
(12, 5, 12),
(13, 5, 13),
(14, 6, 14),
(15, 6, 15),
(16, 6, 16);


