use KURSOVOY;
CREATE TABLE NEWS (
  ID INT NOT NULL AUTO_INCREMENT,
  CREATE_DATE DATETIME NOT NULL,
  CAPTION VARCHAR(45) NOT NULL,
  TEXT TEXT NOT NULL,
  PRIMARY KEY (`ID`));