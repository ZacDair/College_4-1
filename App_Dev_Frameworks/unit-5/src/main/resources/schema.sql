CREATE TABLE Franchise(
    franchiseID INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    franchiseName varchar(255) NOT NULL
);
CREATE TABLE Hero(
    heroID INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    heroName varchar(255) NOT NULL,
    franchiseID INT(11),
    FOREIGN KEY (franchiseID) REFERENCES Franchise(franchiseID)
);