--Create a table for households (contains a unique ID, eircode and address) --
CREATE TABLE Household(
    houseID INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    houseEircode varchar(255) NOT NULL,
    houseAddress varchar(255) NOT NULL
);
--Create a table for people (contains a unique ID, name, age and occupation) --
CREATE TABLE Person(
    personID INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    personName varchar(255) NOT NULL,
    personAge INT(3),
    personOccupation varchar(255) NOT NULL
);
--Create a junction table for houses and their occupants (contains a unique ID, houseID and personID) --
CREATE TABLE OccupantRecords(
    occupantID INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    houseID varchar(255) NOT NULL,
    personID varchar(255) NOT NULL,
    FOREIGN KEY (houseID) REFERENCES Household(houseID),
    FOREIGN KEY (personID) REFERENCES Person(personID)
);

