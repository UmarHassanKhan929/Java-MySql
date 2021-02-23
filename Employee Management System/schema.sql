
DROP SCHEMA IF EXISTS employeepayroll;
CREATE SCHEMA employeepayroll;
USE employeepayroll;


CREATE TABLE Department (
  dept_id int,
  dept_name varchar(20) NOT NULL,
  PRIMARY KEY (dept_id) 
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE GradeScale (
  grade_id int NOT NULL,
  grade_name varchar(50) UNIQUE NOT NULL,
  dept_id int,
  basepay int	NOT NULL,
  travelAllo int,
  medicAllo int,
  houseRentAllo int,
  PRIMARY KEY (grade_id),
  FOREIGN KEY(dept_id) references Department(dept_id) ON UPDATE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE Employee (
  emp_id int,
  SSN int UNIQUE,
  emp_name char(30) NOT NULL,
  address varchar(40),
  email varchar(20),
  phone varchar(15),
  DOB date,
  joining_date date,
  age int ,	
  mgr_emp_id int,
  grade_id int,
  dept_id int,
  
  PRIMARY KEY (emp_id),
  FOREIGN KEY(dept_id) references Department(dept_id) ON DELETE SET NULL ON UPDATE CASCADE, 
  FOREIGN KEY(grade_id) references GradeScale(grade_id) ON DELETE SET NULL ON UPDATE CASCADE

)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

ALTER TABLE Employee
ADD FOREIGN KEY (mgr_emp_id) REFERENCES Employee(emp_id) ON DELETE SET NULL;

CREATE TABLE Payslip (
  pay_id int,
  pay_description varchar(150),
  date_generated date,
  bonus int, 
  #netpay int,	#derived attribute will be used in views
  emp_id int	NOT NULL,
  PRIMARY KEY (pay_id),
  FOREIGN KEY(emp_id) REFERENCES Employee(emp_id) ON DELETE CASCADE ON UPDATE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

ALTER TABLE `employeepayroll`.`payslip` 
ADD COLUMN `month` VARCHAR(45) NOT NULL AFTER `bonus`,
ADD COLUMN `year` VARCHAR(45) NOT NULL AFTER `month`;

ALTER TABLE `employeepayroll`.`payslip` 
CHANGE COLUMN `month` `month` VARCHAR(45) NULL DEFAULT NULL ,
CHANGE COLUMN `year` `year` VARCHAR(45) NULL DEFAULT '2021' ;

ALTER TABLE `employeepayroll`.`payslip` 
CHANGE COLUMN `bonus` `bonus` INT NULL DEFAULT 0 ;

CREATE TABLE Deduction (
  emp_id int NOT NULL,
  loan int,	 #trigger
  numleaves int DEFAULT 0, 
  timesLate int DEFAULT 0,  
  FOREIGN KEY(emp_id) references Employee(emp_id) ON DELETE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE User (
  user_id INT NOT NULL AUTO_INCREMENT,
  emp_id INT NOT NULL,
  username varchar(20) UNIQUE NOT NULL,
  password varchar(20) NOT NULL,
  privelege char(10) CHECK (privelege IN  ("AD","EMP","MGR")),
  PRIMARY KEY (user_id),
  FOREIGN KEY(emp_id) references Employee(emp_id) ON DELETE CASCADE ON UPDATE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DELIMITER //
CREATE TRIGGER wagecheck BEFORE INSERT ON GradeScale 
	FOR EACH ROW 
    BEGIN
		IF NEW.basepay < 5000 THEN SET NEW.basepay = 5000; END IF;
		IF NEW.travelAllo < 0 THEN SET NEW.travelAllo = 0; END IF;
		IF NEW.medicAllo < 0 THEN SET NEW.medicAllo = 0; END IF;
		IF NEW.houseRentAllo < 0 THEN SET NEW.houseRentAllo = 0; END IF;
	END;//
DELIMITER ; 

DELIMITER //
CREATE TRIGGER agecheck BEFORE INSERT ON Employee 
	FOR EACH ROW 
	BEGIN
		IF NEW.age < 0 THEN SET NEW.age = 0; END IF;
	END//
DELIMITER ;


#some changes
ALTER TABLE `employeepayroll`.`deduction` 
CHANGE COLUMN `loan` `loan` INT NOT NULL DEFAULT 0 ;

ALTER TABLE `employeepayroll`.`gradescale` 
CHANGE COLUMN `travelAllo` `travelAllo` INT NULL DEFAULT 0 ,
CHANGE COLUMN `medicAllo` `medicAllo` INT NULL DEFAULT 0 ,
CHANGE COLUMN `houseRentAllo` `houseRentAllo` INT NULL DEFAULT 0 ;

create view emppayslip as
select `p`.`pay_id` AS `pay_id`,`e`.`emp_id` AS `emp_id`,`e`.`emp_name` AS `emp_name`,`e`.`age` AS `age`,`e`.`phone` AS `phone`,`d`.`dept_name` AS `dept_name`,`g`.`grade_name` AS `grade_name`,`ded`.`loan` AS `loan`,`ded`.`numleaves` AS `numleaves`,`ded`.`timesLate` AS `timesLate`,`p`.`pay_description` AS `pay_description`,`p`.`bonus` AS `bonus`,`p`.`month` AS `month`,`p`.`year` AS `year`,`p`.`date_generated` AS `date_generated`,((`g`.`houseRentAllo` + `g`.`medicAllo`) + `g`.`travelAllo`) AS `allowance`,((((`g`.`basepay` + `g`.`houseRentAllo`) + `g`.`medicAllo`) + `g`.`travelAllo`) + `p`.`bonus`) AS `netpay`,`e`.`SSN` AS `ssn` from ((((`employeepayroll`.`employee` `e` join `employeepayroll`.`gradescale` `g` on((`g`.`grade_id` = `e`.`grade_id`))) left join `employeepayroll`.`payslip` `p` on((`p`.`emp_id` = `e`.`emp_id`))) left join `employeepayroll`.`department` `d` on((`e`.`dept_id` = `d`.`dept_id`))) left join `employeepayroll`.`deduction` `ded` on((`e`.`emp_id` = `ded`.`emp_id`)));


ALTER TABLE gradescale DROP INDEX grade_name;

ALTER TABLE employee MODIFY email VARCHAR(200);
ALTER TABLE user MODIFY username VARCHAR(200);

ALTER TABLE payslip ALTER bonus SET DEFAULT 0;
ALTER TABLE payslip ALTER pay_description SET DEFAULT "None";
