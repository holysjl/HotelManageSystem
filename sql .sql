-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema hotel
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema hotel
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `hotel` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `hotel` ;

-- -----------------------------------------------------
-- Table `hotel`.`Room`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hotel`.`Room` (
  `RNo` VARCHAR(10) NOT NULL COMMENT '',
  `Type` VARCHAR(10) NULL COMMENT '',
  `Price` INT NULL COMMENT '',
  PRIMARY KEY (`RNo`)  COMMENT '')
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hotel`.`record`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hotel`.`record` (
  `RecordNo` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '',
  `Room_RNo` VARCHAR(10) NOT NULL COMMENT '',
  `Start_Date` DATE NOT NULL COMMENT '',
  `End_Date` DATE NOT NULL COMMENT '',
  `Paid_Fee` INT NOT NULL COMMENT '',
  `Cash_Pledge` INT NOT NULL COMMENT '',
  PRIMARY KEY (`RecordNo`)  COMMENT '',
  INDEX `fk_record_Room1_idx` (`Room_RNo` ASC)  COMMENT '',
  CONSTRAINT `fk_record_Room1`
    FOREIGN KEY (`Room_RNo`)
    REFERENCES `hotel`.`Room` (`RNo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hotel`.`Customer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hotel`.`Customer` (
  `ID` VARCHAR(18) NOT NULL COMMENT '',
  `CName` VARCHAR(20) NOT NULL COMMENT '',
  `Sex` CHAR(10) NOT NULL COMMENT '',
  `Tel` VARCHAR(15) NULL COMMENT '',
  `record_RecordNo` BIGINT(20) NOT NULL COMMENT '',
  PRIMARY KEY (`ID`)  COMMENT '',
  INDEX `fk_Customer_record1_idx` (`record_RecordNo` ASC)  COMMENT '',
  CONSTRAINT `fk_Customer_record1`
    FOREIGN KEY (`record_RecordNo`)
    REFERENCES `hotel`.`record` (`RecordNo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hotel`.`Booking`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hotel`.`Booking` (
  `BookingNo` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '',
  `Type` VARCHAR(10) NOT NULL COMMENT '',
  `Tel` VARCHAR(15) NOT NULL COMMENT '',
  `CName` VARCHAR(20) NOT NULL COMMENT '',
  `Date_From` DATE NOT NULL COMMENT '',
  `Date_To` DATE NOT NULL COMMENT '',
  PRIMARY KEY (`BookingNo`)  COMMENT '')
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hotel`.`Bill`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hotel`.`Bill` (
  `record_RecordNo` BIGINT(20) NOT NULL COMMENT '',
  `Extra_Fee` INT NOT NULL COMMENT '',
  `Total_Fee` INT NULL COMMENT '',
  INDEX `fk_Bill_record1_idx` (`record_RecordNo` ASC)  COMMENT '',
  PRIMARY KEY (`record_RecordNo`)  COMMENT '',
  CONSTRAINT `fk_Bill_record1`
    FOREIGN KEY (`record_RecordNo`)
    REFERENCES `hotel`.`record` (`RecordNo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hotel`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hotel`.`user` (
  `Account` VARCHAR(15) NOT NULL COMMENT '',
  `pwd` VARCHAR(40) NOT NULL COMMENT '',
  PRIMARY KEY (`Account`)  COMMENT '')
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
