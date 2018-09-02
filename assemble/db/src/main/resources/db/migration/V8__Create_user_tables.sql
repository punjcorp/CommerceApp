-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema pi_pos_industry
-- -----------------------------------------------------
USE `pi_pos_industry` ;

-- -----------------------------------------------------
-- Table `pi_pos_industry`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `pi_pos_industry`.`user` ;

CREATE TABLE IF NOT EXISTS `pi_pos_industry`.`user` (
  `username` VARCHAR(50) NOT NULL,
  `first_name` VARCHAR(50) NOT NULL,
  `last_name` VARCHAR(50) NULL,
  `phone1` VARCHAR(15) NULL,
  `phone2` VARCHAR(15) NULL,
  `email` VARCHAR(100) NULL,
  `login_count` INT NOT NULL DEFAULT 0,
  `status` VARCHAR(2) NOT NULL,
  `default_location` INT(4) NOT NULL,
  `photo` MEDIUMBLOB NULL,
  `photo_type` VARCHAR(120) NULL,
  `photo_url` VARCHAR(300) NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `created_date` DATETIME NOT NULL,
  `modified_by` VARCHAR(50) NULL,
  `modified_date` DATETIME NULL,  
  PRIMARY KEY (`username`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `pi_pos_industry`.`user_password`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `pi_pos_industry`.`user_password` ;

CREATE TABLE IF NOT EXISTS `pi_pos_industry`.`user_password` (
  `password_id` BIGINT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(50) NOT NULL,
  `password` VARCHAR(150) NOT NULL,
  `modified_by` VARCHAR(50) NOT NULL,
  `modified_date` DATETIME NOT NULL,
  `status` VARCHAR(2) NOT NULL DEFAULT 'A',
  PRIMARY KEY (`password_id`),
  CONSTRAINT `fk_user_password_user`
    FOREIGN KEY (`username`)
    REFERENCES `pi_pos_industry`.`user` (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `pi_pos_industry`.`role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `pi_pos_industry`.`role` ;

CREATE TABLE IF NOT EXISTS `pi_pos_industry`.`role` (
  `role_id` INT NOT NULL AUTO_INCREMENT,
  `role_name` VARCHAR(75) NOT NULL,
  `description` VARCHAR(200) NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `created_date` DATETIME NOT NULL,
  PRIMARY KEY (`role_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `pi_pos_industry`.`access`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `pi_pos_industry`.`access` ;

CREATE TABLE IF NOT EXISTS `pi_pos_industry`.`access` (
  `access_id` INT NOT NULL AUTO_INCREMENT,
  `access_name` VARCHAR(100) NOT NULL,
  `description` VARCHAR(200) NULL,
  PRIMARY KEY (`access_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `pi_pos_industry`.`role_access`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `pi_pos_industry`.`role_access` ;

CREATE TABLE IF NOT EXISTS `pi_pos_industry`.`role_access` (
  `role_id` INT NOT NULL,
  `access_id` INT NOT NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `created_date` DATETIME NOT NULL,
  PRIMARY KEY (`role_id`, `access_id`),
  CONSTRAINT `fk_role_access_role1`
    FOREIGN KEY (`role_id`)
    REFERENCES `pi_pos_industry`.`role` (`role_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_role_access_access1`
    FOREIGN KEY (`access_id`)
    REFERENCES `pi_pos_industry`.`access` (`access_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_role_access_role1_idx` ON `pi_pos_industry`.`role_access` (`role_id` ASC);

CREATE INDEX `fk_role_access_access1_idx` ON `pi_pos_industry`.`role_access` (`access_id` ASC);


-- -----------------------------------------------------
-- Table `pi_pos_industry`.`user_role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `pi_pos_industry`.`user_role` ;

CREATE TABLE IF NOT EXISTS `pi_pos_industry`.`user_role` (
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
    REFERENCES `pi_pos_industry`.`role` (`role_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_role_user1`
    FOREIGN KEY (`username`)
    REFERENCES `pi_pos_industry`.`user` (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_role_location1`
    FOREIGN KEY (`location_id`)
    REFERENCES `pi_pos_industry`.`location` (`location_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_user_role_user1_idx` ON `pi_pos_industry`.`user_role` (`username` ASC);

CREATE INDEX `fk_user_role_location1_idx` ON `pi_pos_industry`.`user_role` (`location_id` ASC);


-- -----------------------------------------------------
-- Table `pi_pos_industry`.`user_recovery`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `pi_pos_industry`.`user_recovery` ;

CREATE TABLE IF NOT EXISTS `pi_pos_industry`.`user_recovery` (
  `username` VARCHAR(50) NOT NULL,
  `question1` VARCHAR(150) NOT NULL,
  `answer1` VARCHAR(45) NULL,
  `question2` VARCHAR(150) NULL,
  `answer2` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`username`),
  CONSTRAINT `fk_user_recovery_user1`
    FOREIGN KEY (`username`)
    REFERENCES `pi_pos_industry`.`user` (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `pi_pos_industry`.`login_logs`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `pi_pos_industry`.`login_logs` ;

CREATE TABLE IF NOT EXISTS `pi_pos_industry`.`login_logs` (
  `username` VARCHAR(50) NOT NULL,
  `login_time` DATETIME NOT NULL,
  `login_result` VARCHAR(20) NOT NULL,
  `password_used` VARCHAR(150) NULL,
  PRIMARY KEY (`username`, `login_time`),
  CONSTRAINT `fk_login_logs_user1`
    FOREIGN KEY (`username`)
    REFERENCES `pi_pos_industry`.`user` (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_login_logs_user1_idx` ON `pi_pos_industry`.`login_logs` (`username` ASC);


-- -----------------------------------------------------
-- Table `pi_pos_industry`.`address_master`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `pi_pos_industry`.`address_master` ;

CREATE TABLE IF NOT EXISTS `pi_pos_industry`.`address_master` (
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
-- Table `pi_pos_industry`.`user_credit_debit`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `pi_pos_industry`.`user_credit_debit` ;

CREATE TABLE IF NOT EXISTS `pi_pos_industry`.`user_credit_debit` (
  `username` VARCHAR(50) NOT NULL,
  `card_no` VARCHAR(20) NOT NULL,
  `address_id` BIGINT NOT NULL,
  `name_on_card` VARCHAR(100) NOT NULL,
  `expiry_date` VARCHAR(5) NOT NULL,
  `cvv_no` INT(3) NULL,
  PRIMARY KEY (`username`, `card_no`),
  CONSTRAINT `fk_user_credit_debit_user1`
    FOREIGN KEY (`username`)
    REFERENCES `pi_pos_industry`.`user` (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_credit_debit_user_address1`
    FOREIGN KEY (`address_id`)
    REFERENCES `pi_pos_industry`.`address_master` (`address_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_user_credit_debit_user_address1_idx` ON `pi_pos_industry`.`user_credit_debit` (`address_id` ASC);


-- -----------------------------------------------------
-- Table `pi_pos_industry`.`user_address`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `pi_pos_industry`.`user_address` ;

CREATE TABLE IF NOT EXISTS `pi_pos_industry`.`user_address` (
  `username` VARCHAR(50) NOT NULL,
  `address_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`username`, `address_id`),
  CONSTRAINT `fk_user_address_address_master1`
    FOREIGN KEY (`address_id`)
    REFERENCES `pi_pos_industry`.`address_master` (`address_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_address_user1`
    FOREIGN KEY (`username`)
    REFERENCES `pi_pos_industry`.`user` (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_user_address_user1_idx` ON `pi_pos_industry`.`user_address` (`username` ASC);


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
