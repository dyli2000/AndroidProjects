
CREATE DATABASE CutAnim DEFAULT CHARSET=gb2312;
USE CutAnim


CREATE TABLE tableA(                  
  tid INT(3) NOT NULL,      
  name VARCHAR(23) NOT NULL,                                    
  PRIMARY KEY (tid)
)DEFAULT CHARSET=gb2312;

INSERT INTO tableA VALUES(1,'Kobe');
INSERT INTO tableA VALUES(2,'James');
INSERT INTO tableA VALUES(3,'Wade');