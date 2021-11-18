-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
-- -----------------------------------------------------
-- Schema playssafy
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema playssafy
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `playssafy` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`hospital`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`hospital` (
  `hospitalno` INT NOT NULL AUTO_INCREMENT,
  `sido_code` VARCHAR(10) NOT NULL,
  `guguncode` VARCHAR(10) NOT NULL,
  `hospital_name` VARCHAR(45) NOT NULL,
  `phone` VARCHAR(13) NULL DEFAULT NULL,
  `address_no` INT NOT NULL,
  PRIMARY KEY (`hospitalno`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `mydb`.`product`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`product` (
  `pro_code` INT NOT NULL AUTO_INCREMENT,
  `pro_name` VARCHAR(100) NOT NULL,
  `pro_price` INT NULL DEFAULT NULL,
  PRIMARY KEY (`pro_code`))
ENGINE = InnoDB
AUTO_INCREMENT = 17
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `mydb`.`table1`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`table1` (
  `sid` INT NOT NULL AUTO_INCREMENT,
  `sname` VARCHAR(45) NOT NULL,
  `spwd` VARCHAR(45) NOT NULL,
  `sfavor` VARCHAR(45) NULL DEFAULT NULL,
  `sdate` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`sid`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;

USE `playssafy` ;

-- -----------------------------------------------------
-- Table `playssafy`.`hibernate_sequence`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `playssafy`.`hibernate_sequence` (
  `next_val` BIGINT NULL DEFAULT NULL)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `playssafy`.`mind`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `playssafy`.`mind` (
  `quiz_num` BIGINT NOT NULL,
  `contents` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`quiz_num`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `playssafy`.`speak`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `playssafy`.`speak` (
  `quiz_num` BIGINT NOT NULL,
  `contents` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`quiz_num`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `playssafy`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `playssafy`.`user` (
  `id` VARCHAR(255) NOT NULL,
  `name` VARCHAR(255) NULL DEFAULT NULL,
  `pass` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
