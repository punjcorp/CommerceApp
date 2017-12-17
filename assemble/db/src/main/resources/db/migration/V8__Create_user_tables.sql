-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema commercedb
-- -----------------------------------------------------
USE `commercedb` ;

-- -----------------------------------------------------
-- Table `commercedb`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`user` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`user` (
  `username` VARCHAR(50) NOT NULL,
  `first_name` VARCHAR(50) NOT NULL,
  `last_name` VARCHAR(50) NULL,
  `phone1` VARCHAR(15) NULL,
  `phone2` VARCHAR(15) NULL,
  `email` VARCHAR(100) NOT NULL,
  `login_count` INT NOT NULL DEFAULT 0,
  `status` VARCHAR(2) NOT NULL,
  `photo` BLOB(2000) NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `created_date` DATETIME NOT NULL,
  PRIMARY KEY (`username`))
ENGINE = InnoDB;

CREATE UNIQUE INDEX `email_UNIQUE` ON `commercedb`.`user` (`email` ASC);


-- -----------------------------------------------------
-- Table `commercedb`.`user_password`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`user_password` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`user_password` (
  `username` VARCHAR(50) NOT NULL,
  `password` VARCHAR(150) NOT NULL,
  `modified_by` VARCHAR(50) NOT NULL,
  `modified_date` DATETIME NOT NULL,
  `status` VARCHAR(2) NOT NULL DEFAULT 'A',
  PRIMARY KEY (`username`, `modified_date`, `password`),
  CONSTRAINT `fk_user_password_user`
    FOREIGN KEY (`username`)
    REFERENCES `commercedb`.`user` (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `commercedb`.`role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`role` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`role` (
  `role_id` INT NOT NULL AUTO_INCREMENT,
  `role_name` VARCHAR(75) NOT NULL,
  `description` VARCHAR(200) NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `created_date` DATETIME NOT NULL,
  PRIMARY KEY (`role_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `commercedb`.`access`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`access` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`access` (
  `access_id` INT NOT NULL AUTO_INCREMENT,
  `access_name` VARCHAR(100) NOT NULL,
  `description` VARCHAR(200) NULL,
  PRIMARY KEY (`access_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `commercedb`.`role_access`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`role_access` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`role_access` (
  `role_id` INT NOT NULL,
  `access_id` INT NOT NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `created_date` DATETIME NOT NULL,
  PRIMARY KEY (`role_id`, `access_id`),
  CONSTRAINT `fk_role_access_role1`
    FOREIGN KEY (`role_id`)
    REFERENCES `commercedb`.`role` (`role_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_role_access_access1`
    FOREIGN KEY (`access_id`)
    REFERENCES `commercedb`.`access` (`access_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_role_access_role1_idx` ON `commercedb`.`role_access` (`role_id` ASC);

CREATE INDEX `fk_role_access_access1_idx` ON `commercedb`.`role_access` (`access_id` ASC);


-- -----------------------------------------------------
-- Table `commercedb`.`user_role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`user_role` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`user_role` (
  `username` VARCHAR(50) NOT NULL,
  `role_id` INT NOT NULL,
  `location_id` INT(4) NOT NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `created_date` DATETIME NOT NULL,
  `begin_date` DATETIME NULL,
  `end_date` DATETIME NULL,
  PRIMARY KEY (`username`, `role_id`, `location_id`),
  CONSTRAINT `fk_user_role_role1`
    FOREIGN KEY (`role_id`)
    REFERENCES `commercedb`.`role` (`role_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_role_user1`
    FOREIGN KEY (`username`)
    REFERENCES `commercedb`.`user` (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_role_location1`
    FOREIGN KEY (`location_id`)
    REFERENCES `commercedb`.`location` (`location_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_user_role_user1_idx` ON `commercedb`.`user_role` (`username` ASC);

CREATE INDEX `fk_user_role_location1_idx` ON `commercedb`.`user_role` (`location_id` ASC);


-- -----------------------------------------------------
-- Table `commercedb`.`user_recovery`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`user_recovery` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`user_recovery` (
  `username` VARCHAR(50) NOT NULL,
  `question1` VARCHAR(150) NOT NULL,
  `answer1` VARCHAR(45) NULL,
  `question2` VARCHAR(150) NULL,
  `answer2` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`username`),
  CONSTRAINT `fk_user_recovery_user1`
    FOREIGN KEY (`username`)
    REFERENCES `commercedb`.`user` (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `commercedb`.`login_logs`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`login_logs` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`login_logs` (
  `username` VARCHAR(50) NOT NULL,
  `login_time` DATETIME NOT NULL,
  `login_result` VARCHAR(20) NOT NULL,
  `password_used` VARCHAR(150) NULL,
  PRIMARY KEY (`username`, `login_time`),
  CONSTRAINT `fk_login_logs_user1`
    FOREIGN KEY (`username`)
    REFERENCES `commercedb`.`user` (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_login_logs_user1_idx` ON `commercedb`.`login_logs` (`username` ASC);


-- -----------------------------------------------------
-- Table `commercedb`.`address_master`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`address_master` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`address_master` (
  `address_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `primary` VARCHAR(1) NOT NULL DEFAULT 'N',
  `address_type` VARCHAR(20) NOT NULL,
  `address1` VARCHAR(150) NOT NULL,
  `address2` VARCHAR(150) NULL,
  `city` VARCHAR(30) NOT NULL,
  `state` VARCHAR(45) NOT NULL,
  `country` VARCHAR(45) NOT NULL,
  `pincode` VARCHAR(6) NOT NULL,
  PRIMARY KEY (`address_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `commercedb`.`user_credit_debit`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`user_credit_debit` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`user_credit_debit` (
  `username` VARCHAR(50) NOT NULL,
  `card_no` VARCHAR(20) NOT NULL,
  `address_id` BIGINT NOT NULL,
  `name_on_card` VARCHAR(100) NOT NULL,
  `expiry_date` VARCHAR(5) NOT NULL,
  `cvv_no` INT(3) NULL,
  PRIMARY KEY (`username`, `card_no`),
  CONSTRAINT `fk_user_credit_debit_user1`
    FOREIGN KEY (`username`)
    REFERENCES `commercedb`.`user` (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_credit_debit_user_address1`
    FOREIGN KEY (`address_id`)
    REFERENCES `commercedb`.`address_master` (`address_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_user_credit_debit_user_address1_idx` ON `commercedb`.`user_credit_debit` (`address_id` ASC);

-- -----------------------------------------------------
-- Table `commercedb`.`user_address`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`user_address` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`user_address` (
  `username` VARCHAR(50) NOT NULL,
  `address_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`username`, `address_id`),
  CONSTRAINT `fk_user_address_address_master1`
    FOREIGN KEY (`address_id`)
    REFERENCES `commercedb`.`address_master` (`address_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_address_user1`
    FOREIGN KEY (`username`)
    REFERENCES `commercedb`.`user` (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_user_address_user1_idx` ON `commercedb`.`user_address` (`username` ASC);

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
