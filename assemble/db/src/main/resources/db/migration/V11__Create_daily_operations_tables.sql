-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema commercedb
-- -----------------------------------------------------
USE `commercedb` ;

-- -----------------------------------------------------
-- Table `commercedb`.`tender_master`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`tender_master` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`tender_master` (
  `tender_id` INT(3) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(80) NOT NULL,
  `type` VARCHAR(30) NOT NULL,
  `description` VARCHAR(150) NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `created_date` DATETIME NOT NULL,
  `modified_by` VARCHAR(50) NULL,
  `modified_date` DATETIME NULL,
  `sub_tender_id` INT(3) NULL,
  PRIMARY KEY (`tender_id`),
  CONSTRAINT `fk_tender_master_tender_master1`
    FOREIGN KEY (`sub_tender_id`)
    REFERENCES `commercedb`.`tender_master` (`tender_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_tender_master_tender_master1_idx` ON `commercedb`.`tender_master` (`sub_tender_id` ASC);

-- -----------------------------------------------------
-- Table `commercedb`.`txn_tender_count`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`txn_tender_count` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`txn_tender_count` (
  `location_id` INT(4) NOT NULL,
  `business_date` DATETIME NOT NULL,
  `register` INT(3) NOT NULL,
  `txn_no` INT(5) NOT NULL,
  `txn_type` VARCHAR(30) NOT NULL,
  `tender_id` INT(3) NOT NULL,
  `amount` DECIMAL(12,2) NOT NULL,
  `media_count` BIGINT NOT NULL,
  `actual_amount` DECIMAL(12,2) NULL,
  `actual_media_count` BIGINT NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `created_date` DATETIME NOT NULL,
  `modified_by` VARCHAR(50) NULL,
  `modified_date` DATETIME NULL,
  PRIMARY KEY (`location_id`, `business_date`, `register`, `txn_no`, `tender_id`),
  CONSTRAINT `fk_txn_tndr_count_txn_master1`
    FOREIGN KEY (`location_id` , `business_date` , `register` , `txn_no`)
    REFERENCES `commercedb`.`txn_master` (`location_id` , `business_date` , `register` , `txn_no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_txn_tndr_count_tender_master1`
    FOREIGN KEY (`tender_id`)
    REFERENCES `commercedb`.`tender_master` (`tender_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_txn_tndr_count_tender_master1_idx` ON `commercedb`.`txn_tender_count` (`tender_id` ASC);


-- -----------------------------------------------------
-- Table `commercedb`.`denomination_master`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`denomination_master` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`denomination_master` (
  `denomination_id` INT NOT NULL AUTO_INCREMENT,
  `currency_code` VARCHAR(5) NOT NULL DEFAULT 'INR',
  `code` VARCHAR(15) NOT NULL,
  `value` DECIMAL NOT NULL,
  `description` VARCHAR(45) NULL,
  PRIMARY KEY (`denomination_id`))
ENGINE = InnoDB;

CREATE UNIQUE INDEX `code_UNIQUE` ON `commercedb`.`denomination_master` (`code` ASC);


-- -----------------------------------------------------
-- Table `commercedb`.`txn_tender_denomination`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`txn_tender_denomination` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`txn_tender_denomination` (
  `location_id` INT(4) NOT NULL,
  `business_date` DATETIME NOT NULL,
  `register` INT(3) NOT NULL,
  `txn_no` INT(5) NOT NULL,
  `tender_id` INT(3) NOT NULL,
  `denomination_id` INT NOT NULL,
  `amount` DECIMAL(12,2) NOT NULL,
  `media_count` INT NOT NULL,
  `difference_amount` DECIMAL(12,2) NULL,
  `difference_media_count` INT NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `created_date` DATETIME NOT NULL,
  `modified_by` VARCHAR(50) NULL,
  `modified_date` DATETIME NULL,
  PRIMARY KEY (`location_id`, `business_date`, `register`, `txn_no`, `tender_id`, `denomination_id`),
  CONSTRAINT `fk_txn_tndr_denomination_txn_tndr_count1`
    FOREIGN KEY (`location_id` , `business_date` , `register` , `txn_no` , `tender_id`)
    REFERENCES `commercedb`.`txn_tender_count` (`location_id` , `business_date` , `register` , `txn_no` , `tender_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_txn_tender_denomination_denomination_master1`
    FOREIGN KEY (`denomination_id`)
    REFERENCES `commercedb`.`denomination_master` (`denomination_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_txn_tender_denomination_denomination_master1_idx` ON `commercedb`.`txn_tender_denomination` (`denomination_id` ASC);


-- -----------------------------------------------------
-- Table `commercedb`.`tender_movement`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`tender_movement` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`tender_movement` (
  `location_id` INT(4) NOT NULL,
  `business_date` DATETIME NOT NULL,
  `register` INT(3) NOT NULL,
  `txn_no` INT(5) NOT NULL,
  `txn_type` VARCHAR(50) NOT NULL,
  `reason_code` VARCHAR(30) NOT NULL,
  `amount` DECIMAL(12,2) NOT NULL,
  `from_id` VARCHAR(30) NOT NULL,
  `to_id` VARCHAR(30) NOT NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `created_date` DATETIME NOT NULL,
  `modified_by` VARCHAR(50) NULL,
  `modified_date` DATETIME NULL,
  PRIMARY KEY (`location_id`, `business_date`, `register`, `txn_no`),
  CONSTRAINT `fk_txn_tender_control_txn_master1`
    FOREIGN KEY (`location_id` , `business_date` , `register` , `txn_no`)
    REFERENCES `commercedb`.`txn_master` (`location_id` , `business_date` , `register` , `txn_no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_txn_tender_control_location_repository1`
    FOREIGN KEY (`from_id`)
    REFERENCES `commercedb`.`location_repository` (`repository_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_txn_tender_control_location_repository2`
    FOREIGN KEY (`to_id`)
    REFERENCES `commercedb`.`location_repository` (`repository_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_txn_tender_control_txn_master1_idx` ON `commercedb`.`tender_movement` (`location_id` ASC, `business_date` ASC, `register` ASC, `txn_no` ASC);

CREATE INDEX `fk_txn_tender_control_location_repository1_idx` ON `commercedb`.`tender_movement` (`from_id` ASC);

CREATE INDEX `fk_txn_tender_control_location_repository2_idx` ON `commercedb`.`tender_movement` (`to_id` ASC);


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
