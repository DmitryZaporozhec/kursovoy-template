use KURSOVOY;
CREATE TABLE PHOTO (
  ID INT NOT NULL AUTO_INCREMENT,
  FILE_NAME VARCHAR(45) NULL,
  CONTENT MEDIUMBLOB NULL,
  FILE_TYPE VARCHAR(10) NULL,
  PRIMARY KEY (ID));