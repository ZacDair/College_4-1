-- Populate our Franchise table with two entries --
INSERT INTO Franchise (franchiseID,franchiseName) VALUES
(1, 'Marvel Studios'),
(2, 'DC Comics');

-- Populate our Hero table with multiple heroes from different franchises --
-- Ideally a select statement would be used to find the FK of the franchise --
INSERT INTO Hero (heroID, heroName, franchiseID) VALUES
(1, 'Iron Man', 1),
(2, 'Green Arrow', 2),
(3, 'Thor', 1),
(4, 'Flash', 2),
(5, 'Spiderman', 1),
(6, 'Hulk', 1),
(7, 'Black Canary', 2),
(8, 'Captain America', 1),
(9, 'Deadshot', 2);
